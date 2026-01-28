package com.kotlin.skiservice.dto.tariff.matrix

import java.math.BigDecimal

data class TariffEquipmentTypeMatrixResponse(
    val id: Long,
    val tariff: String,
    val equipmentType: String,
    val pricePerHour: BigDecimal,
)