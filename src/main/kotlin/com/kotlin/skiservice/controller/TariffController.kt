package com.kotlin.skiservice.controller

import com.kotlin.skiservice.dto.tariff.TariffRequest
import com.kotlin.skiservice.dto.tariff.TariffResponse
import com.kotlin.skiservice.entities.Tariff
import com.kotlin.skiservice.service.TariffService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v0.1/tariff")
//@PreAuthorize("hasAnyRole('TARIFF_MANAGER', 'ADMIN')")
class TariffController(
    private val tariffService: TariffService
) {

    @Operation(summary = "Получить тариф по коду. Доступно для ролей (TARIFF_MANAGER, ADMIN)")
    @GetMapping("/{tariffCode}")
    fun get(@PathVariable tariffCode: String) : ResponseEntity<TariffResponse> {
        return ResponseEntity.ok(tariffService.getByCode(tariffCode))
    }

    @Operation(summary = "Получить список тарифов. Доступно для ролей (TARIFF_MANAGER, ADMIN)")
    @GetMapping
    fun get(@RequestParam("page") page: Int, @RequestParam("size") size: Int) : Page<Tariff> {
        return tariffService.getAllTariff(page, size)
    }

    @Operation(summary = "Создать тариф. Доступно для ролей (TARIFF_MANAGER, ADMIN)")
    @PostMapping
    fun create(@RequestBody tariffRequest: TariffRequest) : ResponseEntity<TariffResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(tariffService.createTariff(tariffRequest))
    }

    @Operation(summary = "Обновить тариф. Доступно для ролей (TARIFF_MANAGER, ADMIN)")
    @PatchMapping("/{tariffCode}")
    fun update(@RequestBody tariffRequest: TariffRequest,
               @PathVariable tariffCode: String) : ResponseEntity<TariffResponse> {
        return ResponseEntity.ok(tariffService.updateTariff(tariffCode, tariffRequest))
    }

    @Operation(summary = "Удалить тариф. Доступно для ролей (TARIFF_MANAGER, ADMIN)")
    @DeleteMapping("/{tariffCode}")
    fun delete(@PathVariable tariffCode: String) : ResponseEntity<Void> {
        tariffService.deleteTariff(tariffCode)
        return ResponseEntity.noContent().build()
    }
}
