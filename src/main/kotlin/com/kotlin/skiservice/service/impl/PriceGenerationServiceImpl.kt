package com.kotlin.skiservice.service.impl

import com.kotlin.skiservice.dto.price.PriceResponse
import com.kotlin.skiservice.exception.PriceCalculationException
import com.kotlin.skiservice.repository.TariffEquipmentTypeMatrixRepository
import com.kotlin.skiservice.service.*
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class PriceGenerationServiceImpl(
    private val clientService: ClientService,
    private val rentalService: RentalService,
    private val rentalOrderItemService: RentalOrderItemService,
    private val tariffService: TariffService,
    private val equipmentTypeService: EquipmentTypeService,
    private val tariffEquipmentTypeMatrixRepository: TariffEquipmentTypeMatrixRepository
) : PriceGenerationService {

    override fun generatePriceForTariff(ticketNumber: Int, tariffCode: String, countHours: Int): PriceResponse {
        val tariff = tariffService.getTariffByCode(tariffCode)

        val client = clientService.getClientByTicketNumber(ticketNumber)
        val rental = rentalService.getRentalByClientId(client.id!!)
        val rentalOrderItems = rentalOrderItemService.getRentalOrderItemsByRentalOrderId(rental.id!!).rentalOrderItemList

        var resultPrice: BigDecimal = BigDecimal.ZERO

        for (rentalOrderItem in rentalOrderItems) {
            val equipmentType = equipmentTypeService.findEquipmentTypeByCode(rentalOrderItem.equipmentTypeCode)
            val price = tariffEquipmentTypeMatrixRepository.findByTariffCodeAndEquipmentTypeCode(tariffCode, equipmentType.code)
            if (price.isPresent) {
                resultPrice += price.get().pricePerHour
            } else {
                throw PriceCalculationException("Price for tariff code $tariffCode and equipment type with code ${equipmentType.code} not found")
            }
        }

        val countHoursInDecimal = BigDecimal.valueOf(countHours.toDouble())

        return PriceResponse(
            tariff.code,
            countHours,
            resultPrice * countHoursInDecimal,
        )
    }
}