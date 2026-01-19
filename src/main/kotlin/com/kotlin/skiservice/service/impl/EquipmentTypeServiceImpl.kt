package com.kotlin.skiservice.service.impl

import com.kotlin.skiservice.entities.EquipmentType
import com.kotlin.skiservice.exception.EquipmentTypeNotFoundException
import com.kotlin.skiservice.repository.EquipmentTypeRepository
import com.kotlin.skiservice.service.EquipmentTypeService
import org.springframework.stereotype.Service

@Service
class EquipmentTypeServiceImpl(
    private val equipmentTypeRepository: EquipmentTypeRepository
) : EquipmentTypeService {

    override fun findEquipmentType(type: Long): EquipmentType {
        val equipmentType = equipmentTypeRepository.findById(type)
        if (equipmentType.isPresent) {
            return equipmentType.get()
        } else {
            throw EquipmentTypeNotFoundException("Equipment type with id $type not found")
        }
    }
}