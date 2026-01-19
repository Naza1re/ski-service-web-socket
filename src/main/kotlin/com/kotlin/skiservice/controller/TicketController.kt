package com.kotlin.skiservice.controller

import com.kotlin.skiservice.dto.ticket.TicketResponse
import com.kotlin.skiservice.service.QueueTicketService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v0.1/ticket")
class TicketController(
    private val queueTicketService: QueueTicketService
) {

    @PostMapping
    fun create() : ResponseEntity<TicketResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(queueTicketService.getNewTicket())
    }

    @PatchMapping("/status/{ticketNumber}/{status}")
    fun updateQueue(@PathVariable("ticketNumber") ticketNumber : Int,
                    @PathVariable("status") status: String) : ResponseEntity<TicketResponse> {
        return ResponseEntity.ok(queueTicketService.changeStatus(ticketNumber, status))
    }

}
