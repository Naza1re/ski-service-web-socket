package com.kotlin.skiservice.queue.controller

import com.kotlin.skiservice.queue.dto.QueueResponse
import com.kotlin.skiservice.queue.service.QueueService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v0.1/queue")
class QueueController(
    private val queueService: QueueService
) {

    @Operation(summary = "Двинуть номер по очереди в текущую точку, текущая точка = (queue)")
    @PostMapping("/{queue}/next")
    fun next(@PathVariable queue: String) : ResponseEntity<QueueResponse> {
        return ResponseEntity.ok(queueService.nextTicket(queue))
    }

    @Operation(summary = "Получить информацию по текущей очереди на точке, текущая точка = (queue)")
    @GetMapping("/{queue}")
    fun getQueue(@PathVariable queue: String) : ResponseEntity<QueueResponse> {
        return ResponseEntity.ok(queueService.getQueue(queue))
    }

}