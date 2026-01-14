package com.kotlin.skiservice.queue.service.impl

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

    @Transactional(readOnly = true)
    override fun getQueue(): QueueResponse {
        val current = ticketRepository.findCurrentTicket()
        val next = ticketRepository.findNextWaitingTickets()?.get(0)

        return QueueResponse(
            current = current?.ticketNumber?.toString() ?: "-",
            next = next?.ticketNumber?.toString() ?: "-"
        )
    }

    @Transactional
    override fun nextTicket(): QueueResponse {

        val nextTicket = ticketRepository.findNextWaitingTickets()?.get(0)
            ?: throw RuntimeException("Queue is empty")

        nextTicket.status = "CALLED"
        ticketRepository.save(nextTicket)

        messagingTemplate.convertAndSend(
            "/topic/queue",
            QueueResponse(
                current = nextTicket.ticketNumber.toString(),
                next = ticketRepository.findNextWaitingTickets()!!.get(0)?.ticketNumber?.toString() ?: "-"
            )
        )

        return QueueResponse(
            current = nextTicket.ticketNumber.toString(),
            next = ticketRepository.findNextWaitingTickets()!!.get(0)?.ticketNumber?.toString() ?: "-"
        )
    }
}