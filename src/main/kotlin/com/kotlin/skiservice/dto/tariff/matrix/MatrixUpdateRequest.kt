package com.kotlin.skiservice.dto.tariff.matrix

import java.math.BigDecimal

data class MatrixUpdateRequest(
    val pricePerHour: BigDecimal,
)
