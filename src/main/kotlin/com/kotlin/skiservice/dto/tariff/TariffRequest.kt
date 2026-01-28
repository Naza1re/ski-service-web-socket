package com.kotlin.skiservice.dto.tariff

import java.time.LocalTime

data class TariffRequest(
    val code: String,
    val name: String,
    val description: String,
    val startTime: LocalTime,
    val hours: Int,
)
