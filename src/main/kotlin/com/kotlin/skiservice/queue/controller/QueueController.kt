package com.kotlin.skiservice.queue.controller

import com.kotlin.skiservice.queue.dto.QueueListResponse
import com.kotlin.skiservice.queue.dto.QueueResponse
import com.kotlin.skiservice.queue.service.QueueService
import com.kotlin.skiservice.queue.service.QueueSseService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@RestController
@RequestMapping("/api/v0.1/queue")
class QueueController(
    private val queueService: QueueService,
    private val queueSseService: QueueSseService
) {
    //@PreAuthorize("hasAnyRole('REGISTRATION_MANAGER', 'ADMIN', 'RENTAL_MANAGER', 'PAYMENT_MANAGER')")
    @Operation(summary = "Двинуть номер по очереди в текущую точку, текущая точка = (queue). Доступно для ролей (REGISTRATION_MANAGER, ADMIN, RENTAL_MANAGER, PAYMENT_MANAGER")
    @PostMapping("/{queue}/next")
    fun next(@PathVariable queue: String) : ResponseEntity<QueueResponse> {
        return ResponseEntity.ok(queueService.nextTicket(queue))
    }

    @Operation(summary = "Получить информацию по текущей очереди на точке, текущая точка = (queue)")
    @GetMapping("/{queue}")
    fun getQueue(@PathVariable queue: String) : ResponseEntity<QueueResponse> {
        return ResponseEntity.ok(queueService.getQueue(queue))
    }

    @Operation(summary = "Подписаться на получение обновлений(изменение тикета")
    @GetMapping("/{queue}/stream")
    fun stream(@PathVariable queue: String): SseEmitter {
        return queueSseService.subscribe(queue)
    }

    @Operation(summary = "Получить общий список доступных номеров для точки, текущая точка = (queue)")
    @GetMapping("/all/{queue}")
    fun getAllQueue(@PathVariable queue: String) : ResponseEntity<QueueListResponse> {
        return ResponseEntity.ok(queueService.getAllQueue(queue))
    }

}