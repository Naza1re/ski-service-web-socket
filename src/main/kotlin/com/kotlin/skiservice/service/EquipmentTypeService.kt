package com.kotlin.skiservice.service

import com.kotlin.skiservice.dto.equipment.type.EquipmentTypeResponse
import com.kotlin.skiservice.entities.EquipmentType
import org.springframework.data.domain.Page

interface EquipmentTypeService {
    fun findEquipmentType(id: Long): EquipmentType
    fun findEquipmentById(id: Long): EquipmentTypeResponse
    fun findAllEquipmentBy(page: Int, size: Int): Page<EquipmentType>
    fun findEquipmentTypeByCode(code: String): EquipmentType
}