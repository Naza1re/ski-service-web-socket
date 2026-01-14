package com.kotlin.skiservice.dto.equipment

data class EquipmentRequest(
    val barcode: String,
    val type: String,
    val size: String
)