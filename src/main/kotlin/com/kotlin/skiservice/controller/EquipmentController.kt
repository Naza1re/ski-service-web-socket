package com.kotlin.skiservice.controller

import com.kotlin.skiservice.dto.equipment.EquipmentRequest
import com.kotlin.skiservice.dto.equipment.EquipmentResponse
import com.kotlin.skiservice.entities.Equipment
import com.kotlin.skiservice.service.EquipmentService
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v0.1/equipment")
class EquipmentController(
    private val equipmentService: EquipmentService
) {

    @GetMapping("/{barCode}")
    fun get(@PathVariable("barCode") barCode: String): ResponseEntity<EquipmentResponse> {
        return ResponseEntity.ok(equipmentService.getEquipment(barCode))
    }

    @GetMapping
    fun get(@RequestParam("page") page: Int,@RequestParam("size") size: Int): Page<Equipment> {
        return equipmentService.getPageOfEquipment(page, size)
    }

    @PostMapping
    fun create(@RequestBody equipmentRequest: EquipmentRequest): ResponseEntity<EquipmentResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(equipmentService.createEquipment(equipmentRequest))
    }

    @DeleteMapping("/{barCode}")
    fun delete(@PathVariable barCode: String): ResponseEntity<EquipmentResponse> {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(equipmentService.deleteEquipment(barCode))
    }

    @PatchMapping("/{barCode}")
    fun update(@PathVariable barCode: String,
                      @RequestBody equipmentRequest: EquipmentRequest) : ResponseEntity<EquipmentResponse> {
        return ResponseEntity.ok(equipmentService.updateEquipment(barCode, equipmentRequest))
    }
}
