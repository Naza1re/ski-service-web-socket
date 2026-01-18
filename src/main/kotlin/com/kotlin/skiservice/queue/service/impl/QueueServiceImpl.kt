package com.kotlin.skiservice.queue.service.impl

import com.kotlin.skiservice.entities.QueueTicket
import com.kotlin.skiservice.entities.status.QueueTicketStatus
import com.kotlin.skiservice.entities.status.QueueTicketStatus.IN_PROCESS
import com.kotlin.skiservice.exception.QueueException
import com.kotlin.skiservice.exception.TicketNotFoundException
import com.kotlin.skiservice.queue.dto.QueueResponse
import com.kotlin.skiservice.queue.service.QueueService
import com.kotlin.skiservice.repository.TicketRepository
import org.springframework.data.domain.PageRequest
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
        val current = getCurrent()
        val next = getNext()

        return QueueResponse(
            current = current.ticketNumber.toString(),
            next = next.ticketNumber.toString()
        )
    }

    @Transactional
    override fun nextTicket(): QueueResponse {

        val nextTicket = getNext()

        nextTicket.status = IN_PROCESS
        ticketRepository.save(nextTicket)

        messagingTemplate.convertAndSend(
            "/topic/queue",
            QueueResponse(
                current = nextTicket.ticketNumber.toString(),
                next = getNext().ticketNumber.toString()
            )
        )

        return QueueResponse(
            current = nextTicket.ticketNumber.toString(),
            next = getNext().ticketNumber.toString()
        )
    }

    @Transactional
    override fun deleteQueue() {
        return ticketRepository.deleteAll()
    }

    @Transactional(readOnly = true)
    override fun getByTicketNumber(ticketNUmber: Int): QueueTicket {
        return getOrThrow(ticketNUmber)
    }

    private fun getOrThrow(ticketNumber: Int): QueueTicket {
        val queueTicket = ticketRepository.findByTicketNumber(ticketNumber)
        if (queueTicket.isPresent) {
            return queueTicket.get()
        } else {
            throw TicketNotFoundException("Ticket with number $ticketNumber not found")
        }
    }

    private fun getNext() : QueueTicket {
        return ticketRepository.findNextWaitingTicket(PageRequest.of(0,1))
            ?: throw QueueException("Queue is empty")
    }

    private fun getCurrent() : QueueTicket {
        return ticketRepository.findCurrentTicket((PageRequest.of(0,1)))
            ?: throw QueueException("Current ticket not found")
    }
}