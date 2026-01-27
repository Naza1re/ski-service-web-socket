package com.kotlin.skiservice.dto.rental

import com.kotlin.skiservice.entities.enums.DocumentType

data class RentalOrderResponse(
    val id: Long,
    val clientName: String,
    val clientPhoneNumber: String,
    val documentType: DocumentType,
    val documentNumber: String,
    val status: String,
    val help: String?
)