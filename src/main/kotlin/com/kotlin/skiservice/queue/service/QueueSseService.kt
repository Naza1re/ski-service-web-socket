package com.kotlin.skiservice.queue.service

import com.kotlin.skiservice.queue.dto.QueueResponse
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

interface QueueSseService
{
    fun subscribe(queue: String): SseEmitter
    fun send(queue: String, data: QueueResponse)
}