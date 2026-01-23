package com.kotlin.skiservice.controller

import com.kotlin.skiservice.dto.rental.RentalOrderRequest
import com.kotlin.skiservice.dto.rental.RentalOrderResponse
import com.kotlin.skiservice.entities.RentalOrder
import com.kotlin.skiservice.service.RentalService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v0.1/rental")
class RentalController(
    private val rentalService: RentalService
) {

    @Operation(summary = "Получить список аренд")
    @GetMapping
    fun get(@RequestParam("page") page: Int, @RequestParam("size") size: Int) : ResponseEntity<Page<RentalOrder>> {
        return ResponseEntity.ok(rentalService.getRentalListByPage(page, size))
    }

    @Operation(summary = "Создать аренду")
    @PostMapping
    fun create(@RequestBody rentalOrderRequest: RentalOrderRequest) : ResponseEntity<RentalOrderResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(rentalService.createRental(rentalOrderRequest))
    }

    @Operation(summary = "Удалить аренду")
    @DeleteMapping("/{rentalId}")
    fun delete(@PathVariable rentalId : Long) : ResponseEntity<Void> {
        rentalService.deleteRental(rentalId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}
