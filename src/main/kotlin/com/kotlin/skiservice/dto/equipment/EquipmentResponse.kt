package com.kotlin.skiservice.dto.equipment

data class EquipmentResponse(
    val barcode: String,
    val type: String,
    val size: String,
    val status: String
)