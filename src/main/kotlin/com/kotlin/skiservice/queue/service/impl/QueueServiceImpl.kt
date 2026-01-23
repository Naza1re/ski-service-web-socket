package com.kotlin.skiservice.queue.service.impl

import com.kotlin.skiservice.entities.status.QueueTicketStatus
import com.kotlin.skiservice.queue.dto.QueueResponse
import com.kotlin.skiservice.queue.service.QueueService
import com.kotlin.skiservice.repository.TicketRepository
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueueServiceImpl(
    private val ticketRepository: TicketRepository,
    private val messagingTemplate: SimpMessagingTemplate
) : QueueService {

    companion object {
        private const val TOPIC_QUEUE = "/topic/queue/"
    }

    private val prevStatusMap: Map<QueueTicketStatus, QueueTicketStatus> = mapOf(
        QueueTicketStatus.REGISTRATION to QueueTicketStatus.IN_QUEUE,
        QueueTicketStatus.RENTAL_ORDER to QueueTicketStatus.REGISTRATION,
        QueueTicketStatus.PAYMENT to QueueTicketStatus.RENTAL_ORDER,
        QueueTicketStatus.PROCEED to QueueTicketStatus.PAYMENT
    )

    @Transactional(readOnly = true)
    override fun getQueue(queue: String): QueueResponse {
        val currentStatus = QueueTicketStatus.fromValue(queue) ?: return QueueResponse(null,null)
        return buildAndPublishResponse(currentStatus)
    }

    @Transactional
    override fun nextTicket(queue: String): QueueResponse {
        val currentStatus = QueueTicketStatus.fromValue(queue) ?: return QueueResponse(null,null)

        val prevStatus = prevStatusMap[currentStatus] ?: return buildAndPublishResponse(currentStatus)
        val ticketToMove = ticketRepository.findFirstByStatusOrderByTicketNumberAsc(prevStatus)

        ticketToMove?.let {
            it.status = currentStatus
            ticketRepository.save(it)
        }

        return buildAndPublishResponse(currentStatus)
    }

    private fun buildAndPublishResponse(currentStatus: QueueTicketStatus): QueueResponse {
        val current = ticketRepository
            .findFirstByStatusOrderByTicketNumberDesc(currentStatus)
            ?.ticketNumber
            ?.toString()

        val prevStatus = prevStatusMap[currentStatus]
        val next = prevStatus?.let {
            ticketRepository.findFirstByStatusOrderByTicketNumberAsc(it)?.ticketNumber?.toString()
        }

        val response = QueueResponse(current, next)
        messagingTemplate.convertAndSend(TOPIC_QUEUE + currentStatus.value, response)
        return response
    }
}
