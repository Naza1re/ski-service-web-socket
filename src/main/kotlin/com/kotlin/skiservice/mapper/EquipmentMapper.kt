package com.kotlin.skiservice.mapper

import com.kotlin.skiservice.dto.equipment.EquipmentRequest
import com.kotlin.skiservice.dto.equipment.EquipmentResponse
import com.kotlin.skiservice.entities.Equipment
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface EquipmentMapper {

    @Mapping(target = "type", ignore = true)
    @Mapping(target = "status", constant = "NOT_IN_USE")
    fun toModel(equipmentRequest: EquipmentRequest): Equipment

    @Mapping(target = "type", source = "type.name")
    fun toResponse(equipment: Equipment): EquipmentResponse
}