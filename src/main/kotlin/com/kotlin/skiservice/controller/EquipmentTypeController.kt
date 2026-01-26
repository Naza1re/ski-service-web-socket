package com.kotlin.skiservice.controller

import com.kotlin.skiservice.dto.equipment.type.EquipmentTypeResponse
import com.kotlin.skiservice.entities.EquipmentType
import com.kotlin.skiservice.service.EquipmentTypeService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v0.1/equipment-types")
@PreAuthorize("hasAnyRole('RENTAL_MANAGER', 'ADMIN')")
class EquipmentTypeController(
    private val equipmentTypeService: EquipmentTypeService
) {

    @Operation(summary = "Получить тип оборудования по Id. Доступно для ролей ('RENTAL_MANAGER, ADMIN)")
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long) : ResponseEntity<EquipmentTypeResponse> {
        return ResponseEntity.ok(equipmentTypeService.findEquipmentById(id))
    }

    @Operation(summary = "Получить список оборудования. Доступно для ролей ('RENTAL_MANAGER, ADMIN)")
    @GetMapping
    fun get(@RequestParam("page") page: Int, @RequestParam("size") size: Int): ResponseEntity<Page<EquipmentType>> {
        return ResponseEntity.ok(equipmentTypeService.findAllEquipmentBy(page, size))
    }
}