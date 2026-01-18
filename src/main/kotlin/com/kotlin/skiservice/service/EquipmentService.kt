package com.kotlin.skiservice.service

import com.kotlin.skiservice.dto.equipment.EquipmentRequest
import com.kotlin.skiservice.dto.equipment.EquipmentResponse
import com.kotlin.skiservice.entities.Equipment
import com.kotlin.skiservice.entities.EquipmentType
import org.springframework.data.domain.Page

interface EquipmentService {
    fun getPageOfEquipment(page: Int, size: Int): Page<Equipment>
    fun createEquipment(equipmentRequest: EquipmentRequest): EquipmentResponse
    fun deleteEquipment(barCode: String): EquipmentResponse
    fun getEquipment(barCode: String): EquipmentResponse
    fun updateEquipment(barCode: String, equipmentRequest: EquipmentRequest): EquipmentResponse
    fun getEquipmentByBarcode(barcode: String): Equipment
}