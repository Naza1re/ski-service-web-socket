package com.kotlin.skiservice.queue.controller

import com.kotlin.skiservice.queue.dto.QueueResponse
import com.kotlin.skiservice.queue.service.QueueService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v0.1/queue")
class QueueController(
    private val queueService: QueueService
) {

    @PostMapping("/{queue}/next")
    fun next(@PathVariable queue: String) : ResponseEntity<QueueResponse> {
        return ResponseEntity.ok(queueService.nextTicket(queue))
    }

    @GetMapping("/{registration}")
    fun getQueue(@PathVariable registration: String) : ResponseEntity<QueueResponse> {
        return ResponseEntity.ok(queueService.getQueue(registration))
    }

}