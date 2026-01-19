package com.kotlin.skiservice.service

import com.kotlin.skiservice.dto.rental.RentalOrderResponse
import com.kotlin.skiservice.dto.rental.item.RentalOrderItemListResponse
import com.kotlin.skiservice.dto.rental.item.RentalOrderItemRequest

interface RentalOrderItemService {
    fun addItemToRentalOrder(rentalOrderId: Long, rentalOrderItemRequest: RentalOrderItemRequest): RentalOrderResponse
    fun deleteRentalOrderItem(rentalOrderItemId: Long)
    fun getRentalOrderItemsByRentalOrderId(rentalOrderId: Long): RentalOrderItemListResponse
}