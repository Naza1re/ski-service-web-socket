package com.kotlin.skiservice.dto.skipass

data class FillSkiPassRequest(
    val ticketNumber: Int,
    val barCode: String,
    val tariffCode: String,
    val countHours: Int
)
