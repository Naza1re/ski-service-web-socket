package com.kotlin.skiservice.queue.service.impl

import com.kotlin.skiservice.entities.status.QueueTicketStatus
import com.kotlin.skiservice.queue.dto.QueueListResponse
import com.kotlin.skiservice.queue.dto.QueueResponse
import com.kotlin.skiservice.queue.service.QueueService
import com.kotlin.skiservice.queue.service.QueueSseService
import com.kotlin.skiservice.repository.TicketRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueueServiceImpl(
    private val ticketRepository: TicketRepository,
    private val queueSseService: QueueSseService
) : QueueService {

    private val prevStatusMap: Map<QueueTicketStatus, QueueTicketStatus> = mapOf(
        QueueTicketStatus.REGISTRATION to QueueTicketStatus.IN_QUEUE,
        QueueTicketStatus.RENTAL_ORDER to QueueTicketStatus.REGISTRATION_COMPLETED,
        QueueTicketStatus.PAYMENT to QueueTicketStatus.RENTAL_ORDER_COMPLETED,
        QueueTicketStatus.PROCEED to QueueTicketStatus.PAYMENT
    )

    @Transactional(readOnly = true)
    override fun getQueue(queue: String): QueueResponse {
        val currentStatus = QueueTicketStatus.fromValue(queue) ?: return QueueResponse(null,null)
        return returnResult(currentStatus)
    }

    @Transactional
    override fun nextTicket(queue: String): QueueResponse {
        val currentStatus = QueueTicketStatus.fromValue(queue) ?: return QueueResponse(null,null)

        val prevStatus = prevStatusMap[currentStatus] ?: return returnResult(currentStatus)
        val ticketToMove = ticketRepository.findFirstByStatusOrderByTicketNumberAsc(prevStatus)

        ticketToMove?.let {
            it.status = currentStatus
            ticketRepository.save(it)
        }

        val result = returnResult(currentStatus)
        queueSseService.send(queue, result)

        return result
    }

    @Transactional(readOnly = true)
    override fun getAllQueue(queue: String): QueueListResponse {
        val status = prevStatusMap[QueueTicketStatus.fromValue(queue)] ?: return QueueListResponse(emptyList())

        val tickets = ticketRepository.findAllByStatusOrderByTicketNumberAsc(status)

        if (tickets.isEmpty()) return QueueListResponse(emptyList())
        val list = tickets.map {
            it.ticketNumber
        }


        return QueueListResponse(list)
    }

    private fun returnResult(currentStatus: QueueTicketStatus): QueueResponse {
        val current = ticketRepository
            .findFirstByStatusOrderByTicketNumberDesc(currentStatus)
            ?.ticketNumber
            ?.toString()

        val prevStatus = prevStatusMap[currentStatus]
        val next = prevStatus?.let {
            ticketRepository.findFirstByStatusOrderByTicketNumberAsc(it)?.ticketNumber?.toString()
        }

        val response = QueueResponse(current, next)
        return response
    }
}
