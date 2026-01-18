package com.kotlin.skiservice.dto.equipment

data class EquipmentRequest(
    val barCode: String,
    val type: Long,
    val size: String
)