package com.kotlin.skiservice.dto.price

import java.math.BigDecimal

data class PriceResponse(
    val tariff: String,
    val countHours: Int,
    val price: BigDecimal,
)
