package com.kotlin.skiservice.service

import com.kotlin.skiservice.dto.tariff.matrix.MatrixRequest
import com.kotlin.skiservice.dto.tariff.matrix.MatrixUpdateRequest
import com.kotlin.skiservice.dto.tariff.matrix.TariffEquipmentTypeMatrixResponse
import com.kotlin.skiservice.dto.tariff.matrix.TariffEquipmentTypeMatrixResponseList

interface TariffEquipmentTypeMatrixService {
    fun getMatrix(): TariffEquipmentTypeMatrixResponseList
    fun createMatrix(matrixRequest: MatrixRequest): TariffEquipmentTypeMatrixResponse
    fun updateMatrix(id: Long, matrixUpdateRequest: MatrixUpdateRequest): TariffEquipmentTypeMatrixResponse
    fun deleteMatrix(id: Long)
}