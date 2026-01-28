package com.kotlin.skiservice.dto.rental.item

data class RentalOrderItemResponse(
    val equipmentType: String,
    val equipmentTypeCode: String,
    val equipmentBarCode: String,
)