package com.kotlin.skiservice.service

import com.kotlin.skiservice.entities.EquipmentType

interface EquipmentTypeService {
    fun findEquipmentType(type: Long): EquipmentType
}