package com.kotlin.skiservice.queue.service

import com.kotlin.skiservice.queue.dto.QueueResponse

interface QueueService {
    fun getQueue(): QueueResponse
    fun nextTicket(): QueueResponse
}