package com.kotlin.skiservice.service.impl

import com.kotlin.skiservice.dto.skipass.FillSkiPassRequest
import com.kotlin.skiservice.dto.skipass.SkiPassRequest
import com.kotlin.skiservice.dto.skipass.SkiPassResponse
import com.kotlin.skiservice.entities.RentalOrder
import com.kotlin.skiservice.entities.SkiPass
import com.kotlin.skiservice.entities.Tariff
import com.kotlin.skiservice.entities.status.RentalOrderStatus
import com.kotlin.skiservice.exception.SkiPassAlreadyExistException
import com.kotlin.skiservice.exception.SkiPassNotFoundException
import com.kotlin.skiservice.exception.TariffBorderException
import com.kotlin.skiservice.mapper.SkiPassMapper
import com.kotlin.skiservice.repository.RentalRepository
import com.kotlin.skiservice.repository.SkiPassRepository
import com.kotlin.skiservice.service.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class SkiPassServiceImpl(
    private val skiPassRepository: SkiPassRepository,
    private val skiPassMapper: SkiPassMapper,
    private val tariffService: TariffService,
    private val rentalService: RentalService,
    private val clientService: ClientService,
    private val priceGenerationService: PriceGenerationService,
    private val rentalRepository: RentalRepository,
) : SkiPassService {
    override fun getSkiPass(page: Int, size: Int): Page<SkiPass> {
        val pageRequest = PageRequest.of(page, size)
        return skiPassRepository.findAll(pageRequest)
    }

    @Transactional
    override fun createSkiPass(skiPassRequest: SkiPassRequest): SkiPassResponse {
        validateCreateSkiPass(skiPassRequest)
        val skiPassToSave = SkiPass(
            barCode = skiPassRequest.barCode
        )
        val savedSkiPass = skiPassRepository.save(skiPassToSave)
        return skiPassMapper.toResponse(savedSkiPass)
    }

    @Transactional(readOnly = true)
    override fun getSkiPassByBarCode(barCode: String): SkiPassResponse {
        return skiPassMapper.toResponse(getOrThrow(barCode))
    }

    @Transactional
    override fun deleteSkiPassByBarCode(barCode: String) {
        val skiPass = getOrThrow(barCode)
        skiPassRepository.delete(skiPass)
    }

    @Transactional
    override fun fillSkiPass(barCode: String, fillSkiPassRequest: FillSkiPassRequest): SkiPassResponse {
        val skiPass = getOrThrow(barCode)
        val tariff = tariffService.getTariffByCode(fillSkiPassRequest.tariffCode)

        // Установка время работы скип пасу
        val skiPassToSave = fillDateForSkiPass(skiPass, tariff, fillSkiPassRequest.countHours)

        // Установка клиента к ски пасу
        val client = clientService.getClientByTicketNumber(fillSkiPassRequest.ticketNumber)
        skiPassToSave.client = client

        // Начало аренды
        val rentalOrder = rentalService.getRentalByClientId(client.id!!)

        setPriceToRental(fillSkiPassRequest.ticketNumber, tariff, rentalOrder, fillSkiPassRequest.countHours)
        rentalOrder.status = RentalOrderStatus.ACTIVE

        val savedSkiPass = skiPassRepository.save(skiPassToSave)
        return skiPassMapper.toResponse(savedSkiPass)
    }

    @Transactional
    override fun clearSkiPass(barCode: String): SkiPassResponse {
        val skiPass = getOrThrow(barCode)
        skiPass.client = null
        skiPass.validTo = null
        skiPass.validFrom = null
        val savedSkiPass = skiPassRepository.save(skiPass)
        return skiPassMapper.toResponse(savedSkiPass)
    }

    private fun setPriceToRental(ticketNUmber: Int ,tariff: Tariff, rental: RentalOrder, countHours: Int) {

        val price = priceGenerationService.generatePriceForTariff(
            ticketNUmber,
            tariff.code,
            countHours)
        rental.price = price.price

        rentalRepository.save(rental)
    }

    private fun fillDateForSkiPass(skiPass: SkiPass, tariff: Tariff, countHours: Int): SkiPass {
        val validFrom = LocalDateTime.now()
        val validTo = LocalDateTime.now().plusHours(countHours.toLong())
        val tariffValidTo = tariff.startTime.atDate(LocalDate.now()).plusHours(tariff.hours.toLong())

        // Если время работы ски паса выходят за рамки времени работы тарифа
        if (validTo.isAfter(tariffValidTo)) {
            throw TariffBorderException("Ski pass border valid to is after tariff border validTo. Tariff border is $tariffValidTo")
        }

        skiPass.validFrom = validFrom
        skiPass.validTo = validTo

        return skiPass
    }

    private fun validateCreateSkiPass(skiPassRequest: SkiPassRequest) {
        val skiPass = skiPassRepository.findByBarCode(skiPassRequest.barCode)
        if (skiPass.isPresent) {
            throw SkiPassAlreadyExistException("Ski pass with bar code ${skiPassRequest.barCode} already exist ")
        }
    }

    private fun getOrThrow(skiPassBarCode: String): SkiPass {
        val skiPass = skiPassRepository.findByBarCode(skiPassBarCode)
        if (skiPass.isPresent) {
            return skiPass.get()
        } else {
            throw SkiPassNotFoundException("Ski pass with bar code $skiPassBarCode not found")
        }
    }
}
