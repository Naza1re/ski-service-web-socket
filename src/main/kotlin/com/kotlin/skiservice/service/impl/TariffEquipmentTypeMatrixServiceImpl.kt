package com.kotlin.skiservice.service.impl

import com.kotlin.skiservice.dto.tariff.matrix.MatrixRequest
import com.kotlin.skiservice.dto.tariff.matrix.MatrixUpdateRequest
import com.kotlin.skiservice.dto.tariff.matrix.TariffEquipmentTypeMatrixResponse
import com.kotlin.skiservice.dto.tariff.matrix.TariffEquipmentTypeMatrixResponseList
import com.kotlin.skiservice.entities.TariffEquipmentTypeMatrix
import com.kotlin.skiservice.exception.TariffEquipmentMatrixNotFoundException
import com.kotlin.skiservice.exception.TariffEquipmentTypeMatrixAlreadyExist
import com.kotlin.skiservice.mapper.TariffEquipmentTypeMatrixMapper
import com.kotlin.skiservice.repository.TariffEquipmentTypeMatrixRepository
import com.kotlin.skiservice.service.EquipmentTypeService
import com.kotlin.skiservice.service.TariffEquipmentTypeMatrixService
import com.kotlin.skiservice.service.TariffService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TariffEquipmentTypeMatrixServiceImpl(
    private val tariffEquipmentTypeMatrixRepository: TariffEquipmentTypeMatrixRepository,
    private val tariffEquipmentTypeMatrixMapper: TariffEquipmentTypeMatrixMapper,
    private val equipmentTypeService: EquipmentTypeService,
    private val tariffService: TariffService
) : TariffEquipmentTypeMatrixService {

    @Transactional(readOnly = true)
    override fun getMatrix(): TariffEquipmentTypeMatrixResponseList {
        val matrix = tariffEquipmentTypeMatrixRepository.findAll()
        val list = tariffEquipmentTypeMatrixMapper.toListResponse(matrix)
        return TariffEquipmentTypeMatrixResponseList(
            matrixList = list
        )
    }

    @Transactional
    override fun createMatrix(matrixRequest: MatrixRequest): TariffEquipmentTypeMatrixResponse {
        validateCreateMatrix(matrixRequest)
        val equipmentTypeByCode = equipmentTypeService.findEquipmentTypeByCode(matrixRequest.equipmentTypeCode)
        val tariffByCode = tariffService.getTariffByCode(matrixRequest.tariffCode)
        val matrixToSave = TariffEquipmentTypeMatrix(
            equipmentType = equipmentTypeByCode,
            tariff = tariffByCode,
            pricePerHour = matrixRequest.pricePerHour
        )
        val savedMatrix = tariffEquipmentTypeMatrixRepository.save(matrixToSave)
        return tariffEquipmentTypeMatrixMapper.toResponse(savedMatrix)
    }

    @Transactional
    override fun updateMatrix(id: Long, matrixUpdateRequest: MatrixUpdateRequest): TariffEquipmentTypeMatrixResponse {
        val matrix = getOrThrow(id)
        matrix.pricePerHour = matrixUpdateRequest.pricePerHour
        val savedMatrix = tariffEquipmentTypeMatrixRepository.save(matrix)
        return tariffEquipmentTypeMatrixMapper.toResponse(savedMatrix)
    }

    @Transactional
    override fun deleteMatrix(id: Long) {
        val matrix = getOrThrow(id)
        tariffEquipmentTypeMatrixRepository.delete(matrix)
    }

    private fun validateCreateMatrix(request: MatrixRequest) {
        val matrix = tariffEquipmentTypeMatrixRepository.findByTariffCodeAndEquipmentTypeCode(request.tariffCode, request.equipmentTypeCode)
        if (matrix.isPresent) {
            throw TariffEquipmentTypeMatrixAlreadyExist(
                "Matrix with tariff code ${request.tariffCode} and equipment type code ${request.equipmentTypeCode} already exist"
            )
        }
    }

    private fun getOrThrow(id: Long) : TariffEquipmentTypeMatrix {
        val matrix = tariffEquipmentTypeMatrixRepository.findById(id)
        if (matrix.isPresent) {
            return matrix.get()
        } else {
            throw TariffEquipmentMatrixNotFoundException("Tariff equipment type matrix with id $id not found")
        }
    }
}
