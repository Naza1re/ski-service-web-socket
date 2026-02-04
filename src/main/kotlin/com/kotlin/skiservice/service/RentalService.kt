package com.kotlin.skiservice.service

import com.kotlin.skiservice.dto.rental.RentalOrderRequest
import com.kotlin.skiservice.dto.rental.RentalOrderResponse
import com.kotlin.skiservice.entities.RentalOrder
import org.springframework.data.domain.Page

interface RentalService {
    fun getRentalListByPage(page: Int, size: Int): Page<RentalOrder>
    fun createRental(rentalOrderRequest: RentalOrderRequest): RentalOrderResponse
    fun deleteRental(rentalId: Long)
    fun getRentalById(rentalId: Long): RentalOrder
    fun endRental(id: Long): RentalOrderResponse
    fun startRental(rentalId: Long): RentalOrderResponse
    fun getRentalByClientId(clientId: Long): RentalOrder
    fun getRental(id: Long): RentalOrderResponse
    fun endEdit(rentalId: Long): RentalOrderResponse
}