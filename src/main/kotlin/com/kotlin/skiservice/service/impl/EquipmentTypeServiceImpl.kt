package com.kotlin.skiservice.service.impl

import com.kotlin.skiservice.dto.equipment.type.EquipmentTypeResponse
import com.kotlin.skiservice.entities.EquipmentType
import com.kotlin.skiservice.exception.EquipmentTypeNotFoundException
import com.kotlin.skiservice.mapper.EquipmentTypeMapper
import com.kotlin.skiservice.repository.EquipmentTypeRepository
import com.kotlin.skiservice.service.EquipmentTypeService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class EquipmentTypeServiceImpl(
    private val equipmentTypeRepository: EquipmentTypeRepository,
    private val equipmentTypeMapper: EquipmentTypeMapper
) : EquipmentTypeService {

    override fun findEquipmentType(id: Long): EquipmentType {
        return getOrThrow(id)
    }

    override fun findEquipmentById(id: Long): EquipmentTypeResponse {
        val equipmentType = getOrThrow(id)
        return equipmentTypeMapper.toResponse(equipmentType)
    }

    override fun findAllEquipmentBy(page: Int, size: Int): Page<EquipmentType> {
        val pageRequest = PageRequest.of(page, size)
        return equipmentTypeRepository.findAll(pageRequest)
    }

    override fun findEquipmentTypeByCode(code: String): EquipmentType {
        val equipmentType = equipmentTypeRepository.findByCode(code)
        if (equipmentType.isPresent) {
            return equipmentType.get()
        } else {
            throw EquipmentTypeNotFoundException("Equipment type with code $code not found")
        }
    }

    private fun getOrThrow(id : Long) : EquipmentType {
        val equipmentType = equipmentTypeRepository.findById(id)
        if (equipmentType.isPresent) {
            return equipmentType.get()
        } else {
            throw EquipmentTypeNotFoundException("Equipment type with id $id not found")
        }
    }
}