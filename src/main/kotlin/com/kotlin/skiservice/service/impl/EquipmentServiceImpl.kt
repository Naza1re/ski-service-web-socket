package com.kotlin.skiservice.service.impl

import com.kotlin.skiservice.dto.equipment.EquipmentRequest
import com.kotlin.skiservice.dto.equipment.EquipmentResponse
import com.kotlin.skiservice.entities.Equipment
import com.kotlin.skiservice.exception.EquipmentAlreadyExistException
import com.kotlin.skiservice.exception.EquipmentNotFoundException
import com.kotlin.skiservice.mapper.EquipmentMapper
import com.kotlin.skiservice.repository.EquipmentRepository
import com.kotlin.skiservice.service.EquipmentService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.*

@Service
class EquipmentServiceImpl(
    private val equipmentRepository: EquipmentRepository,
    private val equipmentMapper: EquipmentMapper
) : EquipmentService {
    override fun getPageOfEquipment(page: Int, size: Int): Page<Equipment> {
        val pageRequest = PageRequest.of(page, size)
        return equipmentRepository.findAll(pageRequest)
    }

    override fun createEquipment(equipmentRequest: EquipmentRequest): EquipmentResponse {
        val equipment = findEquipmentByBarCode(equipmentRequest.barcode)

        if (equipment.isPresent) {
            throw EquipmentAlreadyExistException("Equipment with bar code : ${equipment.get().barcode} already exist")
        }

        val equipmentToSave = equipmentMapper.toModel(equipmentRequest)
        equipmentToSave.status = "NEW"
        val savedEquipment = equipmentRepository.save(equipmentToSave)

        return equipmentMapper.toResponse(savedEquipment)
    }

    override fun deleteEquipment(barCode: String): EquipmentResponse {
        val equipment = getOrThrow(barCode)
        equipmentRepository.delete(equipment)

        return equipmentMapper.toResponse(equipment)
    }

    override fun getEquipment(barCode: String): EquipmentResponse {
        return equipmentMapper.toResponse(getOrThrow(barCode))
    }

    override fun updateEquipment(barCode: String, equipmentRequest: EquipmentRequest): EquipmentResponse {
        val equipment = getOrThrow(barCode)
        val equipmentToSave = equipmentMapper.toModel(equipmentRequest)
        equipmentToSave.id = equipment.id
        val savedEquipment = equipmentRepository.save(equipmentToSave)
        return equipmentMapper.toResponse(savedEquipment)
    }

    private fun findEquipmentByBarCode(code: String): Optional<Equipment> {
        return equipmentRepository.findByBarcode(code)
    }

    private fun getOrThrow(barCode: String): Equipment {
        val equipment = findEquipmentByBarCode(barCode)
        if ( equipment.isPresent ) {
            return equipment.get()
        } else {
            throw EquipmentNotFoundException("Equipment not found by barcode : $barCode")
        }
    }
}