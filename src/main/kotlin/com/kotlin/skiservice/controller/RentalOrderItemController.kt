package com.kotlin.skiservice.controller

import com.kotlin.skiservice.dto.rental.RentalOrderResponse
import com.kotlin.skiservice.dto.rental.item.RentalOrderItemListResponse
import com.kotlin.skiservice.dto.rental.item.RentalOrderItemRequest
import com.kotlin.skiservice.service.RentalOrderItemService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v0.1/rental-order-items")
class RentalOrderItemController(
    private val rentalOrderItemService: RentalOrderItemService
)
{
    @Operation(summary = "Получить список оборудования закрепленное за арендой по id аренды")
    @GetMapping("/{rentalOrder}")
    fun get(@PathVariable rentalOrder: Long): ResponseEntity<RentalOrderItemListResponse> {
        return ResponseEntity.ok(rentalOrderItemService.getRentalOrderItemsByRentalOrderId(rentalOrder))
    }

    @Operation(summary = "Добавить оборудование по barCode к аренде")
    @PatchMapping("/{rentalOrderId}/add")
    fun add(@PathVariable("rentalOrderId") rentalOrderId : Long,
            @RequestBody rentalOrderItemRequest: RentalOrderItemRequest
    ) : ResponseEntity<RentalOrderResponse> {
        return ResponseEntity.ok(rentalOrderItemService.addItemToRentalOrder(rentalOrderId, rentalOrderItemRequest))
    }

    @Operation(summary = "Удалить оборудование с аренды по id арендованной единицы")
    @DeleteMapping("/{rentalOrderItemId}")
    fun delete(@PathVariable("rentalOrderItemId") rentalOrderItemId : Long) : ResponseEntity<Void> {
        rentalOrderItemService.deleteRentalOrderItem(rentalOrderItemId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}
