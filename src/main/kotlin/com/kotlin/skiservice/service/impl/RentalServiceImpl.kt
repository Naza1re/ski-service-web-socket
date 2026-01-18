package com.kotlin.skiservice.service.impl

import com.kotlin.skiservice.dto.rental.RentalOrderRequest
import com.kotlin.skiservice.dto.rental.RentalOrderResponse
import com.kotlin.skiservice.entities.RentalOrder
import com.kotlin.skiservice.entities.status.RentalOrderStatus
import com.kotlin.skiservice.exception.RentalOrderException
import com.kotlin.skiservice.mapper.RentalOrderMapper
import com.kotlin.skiservice.repository.RentalRepository
import com.kotlin.skiservice.service.ClientService
import com.kotlin.skiservice.service.RentalService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class RentalServiceImpl(
    private val rentalRepository: RentalRepository,
    private val clientService: ClientService,
    private val rentalOrderMapper: RentalOrderMapper,
) : RentalService {

    @Transactional(readOnly = true)
    override fun getRentalListByPage(page: Int, size: Int): Page<RentalOrder> {
        val pageRequest = PageRequest.of(page, size)
        val result = rentalRepository.findAll(pageRequest)
        return result
    }

    @Transactional
    override fun createRental(rentalOrderRequest: RentalOrderRequest): RentalOrderResponse {
        val client = clientService.getClientByTicketNumber(rentalOrderRequest.ticketNumber)
        val rentalOrder = RentalOrder(
            createdAt = LocalDateTime.now(),
            client = client,
            status = RentalOrderStatus.CREATED,
        )
        val savedRental = rentalRepository.save(rentalOrder)
        return rentalOrderMapper.toResponse(savedRental)
    }

    @Transactional
    override fun deleteRental(rentalId: Long) {
        val rental = getOrThrow(rentalId)
        rentalRepository.delete(rental)
    }

    @Transactional(readOnly = true)
    override fun getRentalById(rentalId: Long): RentalOrder {
        return getOrThrow(rentalId)
    }

    private fun getOrThrow(id : Long) : RentalOrder {
        val rentalOrder = rentalRepository.findById(id)
        if (rentalOrder.isPresent) {
            return rentalOrder.get()
        } else {
            throw RentalOrderException("Rental order with id $id not found.")
        }
    }
}
