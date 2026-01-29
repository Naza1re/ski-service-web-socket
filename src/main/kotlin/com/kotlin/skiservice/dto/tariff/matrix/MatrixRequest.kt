package com.kotlin.skiservice.dto.tariff.matrix

import java.math.BigDecimal

data class MatrixRequest(
    val tariffCode: String,
    val equipmentTypeCode: String,
    val pricePerHour: BigDecimal,
)
