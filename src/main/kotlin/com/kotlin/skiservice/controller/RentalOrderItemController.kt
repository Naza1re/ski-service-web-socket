package com.kotlin.skiservice.controller

import com.kotlin.skiservice.dto.rental.RentalOrderResponse
import com.kotlin.skiservice.dto.rental.item.RentalOrderItemListResponse
import com.kotlin.skiservice.dto.rental.item.RentalOrderItemRequest
import com.kotlin.skiservice.service.RentalOrderItemService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v0.1/rental-order-items")
@PreAuthorize("hasAnyRole('RANTAL_MANAGER', 'ADMIN')")
class RentalOrderItemController(
    private val rentalOrderItemService: RentalOrderItemService
)
{
    @Operation(summary = "Получить список оборудования закрепленное за арендой по id аренды. Доступно для ролей ('RENTAL_MANAGER, ADMIN)")
    @GetMapping("/{rentalOrder}")
    fun get(@PathVariable rentalOrder: Long): ResponseEntity<RentalOrderItemListResponse> {
        return ResponseEntity.ok(rentalOrderItemService.getRentalOrderItemsByRentalOrderId(rentalOrder))
    }

    @Operation(summary = "Добавить оборудование по barCode к аренде. Доступно для ролей ('RENTAL_MANAGER, ADMIN)")
    @PatchMapping("/{rentalOrderId}/add")
    fun add(@PathVariable("rentalOrderId") rentalOrderId : Long,
            @RequestBody rentalOrderItemRequest: RentalOrderItemRequest
    ) : ResponseEntity<RentalOrderResponse> {
        return ResponseEntity.ok(rentalOrderItemService.addItemToRentalOrder(rentalOrderId, rentalOrderItemRequest))
    }

    @Operation(summary = "Удалить оборудование с аренды по id арендованной единицы. Доступно для ролей ('RENTAL_MANAGER, ADMIN)")
    @DeleteMapping("/{rentalOrderItemId}")
    fun delete(@PathVariable("rentalOrderItemId") rentalOrderItemId : Long) : ResponseEntity<Void> {
        rentalOrderItemService.deleteRentalOrderItem(rentalOrderItemId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}
