package com.kotlin.skiservice.dto.skipass

import java.math.BigDecimal
import java.time.LocalDateTime

data class SkiPassResponse(
    val barCode: String,
    val validFrom: LocalDateTime?,
    val validTo: LocalDateTime?,
    val price: BigDecimal?,
    val clientName: String?,
)
