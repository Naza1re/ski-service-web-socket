package com.kotlin.skiservice.dto.equipment

data class EquipmentRequest(
    val barCode: String,
    val type: String,
    val size: String
)