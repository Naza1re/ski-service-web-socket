package com.kotlin.skiservice.mapper

import com.kotlin.skiservice.dto.equipment.EquipmentRequest
import com.kotlin.skiservice.dto.equipment.EquipmentResponse
import com.kotlin.skiservice.entities.Equipment
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface EquipmentMapper {

    fun toModel(equipmentRequest: EquipmentRequest): Equipment
    fun toResponse(equipment: Equipment): EquipmentResponse
}