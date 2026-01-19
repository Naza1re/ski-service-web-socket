package com.kotlin.skiservice.dto.rental.item

data class RentalOrderItemListResponse(
    val rentalOderId: Long,
    val rentalOrderItemList: List<RentalOrderItemResponse>
)