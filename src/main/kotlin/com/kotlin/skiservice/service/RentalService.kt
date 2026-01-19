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
}