package com.kotlin.skiservice.controller

import com.kotlin.skiservice.dto.price.PriceResponse
import com.kotlin.skiservice.service.PriceGenerationService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v0.1/price")
//@PreAuthorize("hasAnyRole('PAYMENT_MANAGER','TARIFF_MANAGER', 'ADMIN')")
class PriceGenerationController(
    private val priceGenerationService: PriceGenerationService
) {

    @Operation(summary = "Получить цену за прокат. Доступно для ролей (PAYMENT_MANAGER,TARIFF_MANAGER, ADMIN)")
    @GetMapping("/generate")
    fun getPrice(@RequestParam("tariffCode") tariffCode: String,
                 @RequestParam("countHours") countHours: Int,
                 @RequestParam("ticketNumber") ticketNumber: Int) : ResponseEntity<PriceResponse> {
        return ResponseEntity.ok(priceGenerationService.generatePriceForTariff(ticketNumber,tariffCode, countHours))
    }
}