package com.kotlin.skiservice.controller

import com.kotlin.skiservice.dto.equipment.EquipmentRequest
import com.kotlin.skiservice.dto.equipment.EquipmentResponse
import com.kotlin.skiservice.entities.Equipment
import com.kotlin.skiservice.service.EquipmentService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v0.1/equipment")
//@PreAuthorize("hasAnyRole('RENTAL_MANAGER', 'ADMIN')")
class EquipmentController(
    private val equipmentService: EquipmentService
) {
    @Operation(summary = "Получить информацию по оборудованию. Доступно для ролей ('RENTAL_MANAGER, ADMIN)")
    @GetMapping("/{barCode}")
    fun get(@PathVariable("barCode") barCode: String): ResponseEntity<EquipmentResponse> {
        return ResponseEntity.ok(equipmentService.getEquipment(barCode))
    }

    @Operation(summary = "Получить список оборудования. Доступно для ролей ('RENTAL_MANAGER, ADMIN)")
    @GetMapping
    fun get(@RequestParam("page") page: Int, @RequestParam("size") size: Int): Page<Equipment> {
        return equipmentService.getPageOfEquipment(page, size)
    }

    @Operation(summary = "Добавить оборудование. Доступно для ролей ('RENTAL_MANAGER, ADMIN)")
    @PostMapping
    fun create(@RequestBody equipmentRequest: EquipmentRequest): ResponseEntity<EquipmentResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(equipmentService.createEquipment(equipmentRequest))
    }

    @Operation(summary = "Удалить оборудование. Доступно для ролей ('RENTAL_MANAGER, ADMIN)")
    @DeleteMapping("/{barCode}")
    fun delete(@PathVariable barCode: String): ResponseEntity<EquipmentResponse> {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(equipmentService.deleteEquipment(barCode))
    }

    @Operation(summary = "Обновить информацию по оборудованию. Доступно для ролей ('RENTAL_MANAGER, ADMIN)")
    @PatchMapping("/{barCode}")
    fun update(@PathVariable barCode: String,
                      @RequestBody equipmentRequest: EquipmentRequest) : ResponseEntity<EquipmentResponse> {
        return ResponseEntity.ok(equipmentService.updateEquipment(barCode, equipmentRequest))
    }
}
