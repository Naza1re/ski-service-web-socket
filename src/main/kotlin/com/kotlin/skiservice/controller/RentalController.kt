package com.kotlin.skiservice.controller

import com.kotlin.skiservice.dto.rental.RentalOrderRequest
import com.kotlin.skiservice.dto.rental.RentalOrderResponse
import com.kotlin.skiservice.entities.RentalOrder
import com.kotlin.skiservice.service.RentalService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v0.1/rental")
//@PreAuthorize("hasAnyRole('RENTAL_MANAGER', 'ADMIN')")
class RentalController(
    private val rentalService: RentalService
) {

    @Operation(summary = "Получить список аренд. Доступно для ролей ('RENTAL_MANAGER, ADMIN)")
    @GetMapping
    fun get(@RequestParam("page") page: Int, @RequestParam("size") size: Int) : ResponseEntity<Page<RentalOrder>> {
        return ResponseEntity.ok(rentalService.getRentalListByPage(page, size))
    }

    @Operation(summary = "Получить арену по id")
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long) : ResponseEntity<RentalOrderResponse> {
        return ResponseEntity.ok(rentalService.getRental(id))
    }

    @Operation(summary = "Создать аренду. Доступно для ролей ('RENTAL_MANAGER, ADMIN)")
    @PostMapping
    fun create(@RequestBody rentalOrderRequest: RentalOrderRequest) : ResponseEntity<RentalOrderResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(rentalService.createRental(rentalOrderRequest))
    }

    @Operation(summary = "Закончить редактирование аренды")
    @PutMapping("/end-edit/{rentalId}")
    fun endEdit(@PathVariable rentalId: Long) : ResponseEntity<RentalOrderResponse> {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(rentalService.endEdit(rentalId))
    }

    @Operation(summary = "Завершить аренду. Доступно для ролей ('RENTAL_MANAGER, ADMIN)")
    @PutMapping("/{rentalId}/end")
    fun endRental(@PathVariable rentalId: Long) : ResponseEntity<RentalOrderResponse> {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(rentalService.endRental(rentalId))
    }

    @Operation(summary = "Удалить аренду. Доступно для ролей ('RENTAL_MANAGER, ADMIN)")
    @DeleteMapping("/{rentalId}")
    fun delete(@PathVariable rentalId : Long) : ResponseEntity<Void> {
        rentalService.deleteRental(rentalId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}
