package com.kotlin.skiservice.dto.rental

import com.kotlin.skiservice.entities.enums.DocumentType
import java.math.BigDecimal

data class RentalOrderResponse(
    val id: Long,
    val clientName: String,
    val clientPhoneNumber: String,
    val documentType: DocumentType,
    val documentNumber: String,
    val status: String,
    val price: BigDecimal,
    val help: String?
)