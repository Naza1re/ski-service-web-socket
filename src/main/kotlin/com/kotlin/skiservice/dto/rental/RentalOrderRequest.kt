package com.kotlin.skiservice.dto.rental

data class RentalOrderRequest(
    val ticketNumber: Long,
    val documentType: String,
    val documentNumber: String,
    val cellNumber: String
)