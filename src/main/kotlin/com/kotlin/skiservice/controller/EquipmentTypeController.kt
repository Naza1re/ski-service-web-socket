package com.kotlin.skiservice.controller

import com.kotlin.skiservice.dto.equipment.type.EquipmentTypeResponse
import com.kotlin.skiservice.entities.EquipmentType
import com.kotlin.skiservice.service.EquipmentTypeService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v0.1/equipment-types")
class EquipmentTypeController(
    private val equipmentTypeService: EquipmentTypeService
) {

    @Operation(summary = "Получить тип оборудования по Id")
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long) : ResponseEntity<EquipmentTypeResponse> {
        return ResponseEntity.ok(equipmentTypeService.findEquipmentById(id))
    }

    @Operation(summary = "Получить список оборудования")
    @GetMapping
    fun get(@RequestParam("page") page: Int, @RequestParam("size") size: Int): ResponseEntity<Page<EquipmentType>> {
        return ResponseEntity.ok(equipmentTypeService.findAllEquipmentBy(page, size))
    }
}