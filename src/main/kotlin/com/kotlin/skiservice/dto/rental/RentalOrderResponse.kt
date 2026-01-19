package com.kotlin.skiservice.dto.rental

data class RentalOrderResponse(
    val id: Long,
    val clientId: Long,
    val status: String,
    val help: String?
)