package com.kotlin.skiservice.controller

import com.kotlin.skiservice.dto.rental.RentalOrderResponse
import com.kotlin.skiservice.dto.rental.item.RentalOrderItemListResponse
import com.kotlin.skiservice.dto.rental.item.RentalOrderItemRequest
import com.kotlin.skiservice.service.RentalOrderItemService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v0.1/rental-order-items")
class RentalOrderItemController(
    private val rentalOrderItemService: RentalOrderItemService
)
{
    @GetMapping("/{rentalOrder}")
    fun get(@PathVariable rentalOrder: Long): ResponseEntity<RentalOrderItemListResponse> {
        return ResponseEntity.ok(rentalOrderItemService.getRentalOrderItemsByRentalOrderId(rentalOrder))
    }

    @PatchMapping("/{rentalOrderId}/add")
    fun add(@PathVariable("rentalOrderId") rentalOrderId : Long,
            @RequestBody rentalOrderItemRequest: RentalOrderItemRequest
    ) : ResponseEntity<RentalOrderResponse> {
        return ResponseEntity.ok(rentalOrderItemService.addItemToRentalOrder(rentalOrderId, rentalOrderItemRequest))
    }

    @DeleteMapping("/{rentalOrderId}")
    fun delete(@PathVariable("rentalOrderId") rentalOrderItemId : Long) : ResponseEntity<Void> {
        rentalOrderItemService.deleteRentalOrderItem(rentalOrderItemId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}
