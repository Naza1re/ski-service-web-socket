package com.kotlin.skiservice.controller

import com.kotlin.skiservice.dto.skipass.FillSkiPassRequest
import com.kotlin.skiservice.dto.skipass.SkiPassRequest
import com.kotlin.skiservice.dto.skipass.SkiPassResponse
import com.kotlin.skiservice.entities.SkiPass
import com.kotlin.skiservice.service.SkiPassService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v0.1/ski-pass")
//@PreAuthorize("hasAnyRole('EQUIPMENT_MANAGER', 'PAYMENT_MANAGER', 'ADMIN')")
class SkiPassController(
    private val skiPassService: SkiPassService
) {

    @Operation(summary = "Получить список ски пасов. Доступно для ролей (EQUIPMENT_MANAGER, PAYMENT_MANAGER, ADMIN)")
    @GetMapping
    fun get(@RequestParam("page") page: Int, @RequestParam("size") size: Int) : Page<SkiPass> {
        return skiPassService.getSkiPass(page, size)
    }

    @Operation(summary = "Получить ски пасс по barCode. Доступно для ролей (EQUIPMENT_MANAGER, PAYMENT_MANAGER, ADMIN)")
    @GetMapping("/{barCode}")
    fun get(@PathVariable("barCode") barCode: String) : ResponseEntity<SkiPassResponse> {
        return ResponseEntity.ok(skiPassService.getSkiPassByBarCode(barCode))
    }

    @Operation(summary = "Создать ски пас. Доступно для ролей (EQUIPMENT_MANAGER, PAYMENT_MANAGER, ADMIN)")
    @PostMapping
    fun create(@RequestBody skiPassRequest: SkiPassRequest) : ResponseEntity<SkiPassResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(skiPassService.createSkiPass(skiPassRequest))
    }

    @Operation(summary = "Удалить ски пас. Доступно для ролей (EQUIPMENT_MANAGER, PAYMENT_MANAGER, ADMIN)")
    @DeleteMapping("/{barCode}")
    fun delete(@PathVariable("barCode") barCode: String) : ResponseEntity<Void> {
        skiPassService.deleteSkiPassByBarCode(barCode)
        return ResponseEntity.noContent().build()
    }

    @Operation(summary = "Заполнить ски пас данными по тарифу")
    @PutMapping("/fill/{barCode}")
    fun fillSkiPass(@PathVariable("barCode") barCode: String,
                    @RequestBody fillSkiPassRequest: FillSkiPassRequest
    ) : ResponseEntity<SkiPassResponse> {
        return ResponseEntity.ok(skiPassService.fillSkiPass(barCode, fillSkiPassRequest))
    }

    @Operation(summary = "Очистить данные у ски паса")
    @PutMapping("/clear/{barCode}")
    fun clearSkiPass(@PathVariable("barCode") barCode: String) : ResponseEntity<SkiPassResponse> {
        return ResponseEntity.ok(skiPassService.clearSkiPass(barCode))
    }
}