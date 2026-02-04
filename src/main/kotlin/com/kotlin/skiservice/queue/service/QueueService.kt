package com.kotlin.skiservice.queue.service

import com.kotlin.skiservice.queue.dto.QueueListResponse
import com.kotlin.skiservice.queue.dto.QueueResponse

interface QueueService {
    fun getQueue(queue: String): QueueResponse
    fun nextTicket(queue: String): QueueResponse
    fun getAllQueue(queue: String): QueueListResponse
}