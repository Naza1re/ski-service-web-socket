package com.kotlin.skiservice.controller

import com.kotlin.skiservice.dto.tariff.matrix.MatrixRequest
import com.kotlin.skiservice.dto.tariff.matrix.MatrixUpdateRequest
import com.kotlin.skiservice.dto.tariff.matrix.TariffEquipmentTypeMatrixResponse
import com.kotlin.skiservice.dto.tariff.matrix.TariffEquipmentTypeMatrixResponseList
import com.kotlin.skiservice.service.TariffEquipmentTypeMatrixService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v0.1/tariff-equipment-type-matrix")
//@PreAuthorize("hasAnyRole('TARIFF_MANAGER', 'ADMIN')")
class TariffEquipmentTypeMatrixController(
    private val tariffEquipmentTypeMatrixService: TariffEquipmentTypeMatrixService
) {

    @Operation(summary = "Создать элемент матрицы тариф x тип оборудования = цена за час. Доступно для ролей (TARIFF_MANAGER, ADMIN)")
    @PostMapping
    fun create(@RequestBody matrixRequest: MatrixRequest) : ResponseEntity<TariffEquipmentTypeMatrixResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(tariffEquipmentTypeMatrixService.createMatrix(matrixRequest))
    }

    @Operation(summary = "Установить цену за один час у оборудования по тарифу. Доступно для ролей (TARIFF_MANAGER, ADMIN)")
    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody matrixUpdateRequest: MatrixUpdateRequest ) : ResponseEntity<TariffEquipmentTypeMatrixResponse> {
        return ResponseEntity.ok(tariffEquipmentTypeMatrixService.updateMatrix(id, matrixUpdateRequest))
    }

    @Operation(summary = "Получение матрицы цены. Колонка = оборудование, строка = тариф, значение цена за час проката. Доступно для ролей (TARIFF_MANAGER, ADMIN)")
    @GetMapping
    fun get() : ResponseEntity<TariffEquipmentTypeMatrixResponseList> {
        return ResponseEntity.ok(tariffEquipmentTypeMatrixService.getMatrix())
    }

    @Operation(summary = "Удалить элемент матрицы. Доступно для ролей (TARIFF_MANAGER, ADMIN)")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) : ResponseEntity<Void> {
        tariffEquipmentTypeMatrixService.deleteMatrix(id)
        return ResponseEntity.noContent().build()
    }

}