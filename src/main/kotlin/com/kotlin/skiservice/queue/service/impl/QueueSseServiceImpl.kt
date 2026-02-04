package com.kotlin.skiservice.queue.service.impl

import com.kotlin.skiservice.queue.dto.QueueResponse
import com.kotlin.skiservice.queue.service.QueueSseService
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.ConcurrentHashMap

@Service
class QueueSseServiceImpl : QueueSseService {

    private val emitters = ConcurrentHashMap<String, MutableList<SseEmitter>>()

    override fun subscribe(queue: String): SseEmitter {
        val emitter = SseEmitter(0L)

        emitters.computeIfAbsent(queue) { mutableListOf() }.add(emitter)
        emitter.onCompletion {
            emitters[queue]?.remove(emitter)
        }
        emitter.onTimeout {
            emitters[queue]?.remove(emitter)
        }

        return emitter
    }

    override fun send(queue: String, data: QueueResponse) {
        val dead = mutableListOf<SseEmitter>()
        emitters[queue]?.forEach {
            try {
                it.send(SseEmitter.event().name("queue").data(data))
            } catch (e: Exception) {
                dead.add(it)
            }
        }
        emitters[queue]?.removeAll(dead)
    }
}
