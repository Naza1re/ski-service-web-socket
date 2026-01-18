package com.kotlin.skiservice.service.impl

import com.kotlin.skiservice.dto.equipment.EquipmentRequest
import com.kotlin.skiservice.dto.equipment.EquipmentResponse
import com.kotlin.skiservice.entities.Equipment
import com.kotlin.skiservice.entities.status.EquipmentStatus
import com.kotlin.skiservice.exception.EquipmentAlreadyExistException
import com.kotlin.skiservice.exception.EquipmentNotFoundException
import com.kotlin.skiservice.mapper.EquipmentMapper
import com.kotlin.skiservice.repository.EquipmentRepository
import com.kotlin.skiservice.service.EquipmentService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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

    @Transactional
    override fun createEquipment(equipmentRequest: EquipmentRequest): EquipmentResponse {
        val equipment = findEquipmentByBarCode(equipmentRequest.barcode)

        if (equipment.isPresent) {
            throw EquipmentAlreadyExistException("Equipment with bar code : ${equipment.get().barcode} already exist")
        }

        val equipmentToSave = equipmentMapper.toModel(equipmentRequest)
        equipmentToSave.status = EquipmentStatus.NOT_IN_USE
        val savedEquipment = equipmentRepository.save(equipmentToSave)

        return equipmentMapper.toResponse(savedEquipment)
    }

    @Transactional
    override fun deleteEquipment(barCode: String): EquipmentResponse {
        val equipment = getOrThrow(barCode)
        equipmentRepository.delete(equipment)

        return equipmentMapper.toResponse(equipment)
    }

    @Transactional(readOnly = true)
    override fun getEquipment(barCode: String): EquipmentResponse {
        return equipmentMapper.toResponse(getOrThrow(barCode))
    }

    @Transactional
    override fun updateEquipment(barCode: String, equipmentRequest: EquipmentRequest): EquipmentResponse {
        val equipment = getOrThrow(barCode)
        val equipmentToSave = equipmentMapper.toModel(equipmentRequest)
        equipmentToSave.id = equipment.id
        val savedEquipment = equipmentRepository.save(equipmentToSave)
        return equipmentMapper.toResponse(savedEquipment)
    }

    @Transactional(readOnly = true)
    override fun getEquipmentByBarcode(barcode: String): Equipment {
        return getOrThrow(barcode)
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