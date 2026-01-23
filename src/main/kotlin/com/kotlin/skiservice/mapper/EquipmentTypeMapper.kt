package com.kotlin.skiservice.mapper

import com.kotlin.skiservice.dto.equipment.type.EquipmentTypeResponse
import com.kotlin.skiservice.entities.EquipmentType
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface EquipmentTypeMapper {
    fun toResponse(equipmentType: EquipmentType): EquipmentTypeResponse
}