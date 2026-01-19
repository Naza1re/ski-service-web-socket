package com.kotlin.skiservice.service.impl

import com.kotlin.skiservice.dto.ticket.TicketResponse
import com.kotlin.skiservice.entities.QueueTicket
import com.kotlin.skiservice.entities.status.QueueTicketStatus
import com.kotlin.skiservice.entities.status.QueueTicketStatus.IN_QUEUE
import com.kotlin.skiservice.exception.TicketNotFoundException
import com.kotlin.skiservice.mapper.QueueTicketMapper
import com.kotlin.skiservice.repository.TicketRepository
import com.kotlin.skiservice.service.QueueTicketService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class QueueTicketServiceImpl(
    private val ticketRepository: TicketRepository,
    private val ticketMapper: QueueTicketMapper
) : QueueTicketService {

    @Transactional
    override fun getNewTicket(): TicketResponse {
        val maxTicketNumber = ticketRepository.findMaxTicketNumber() ?: 0
        val ticket = QueueTicket(ticketNumber = maxTicketNumber + 1, status = IN_QUEUE)
        val savedTicket = ticketRepository.save(ticket)
        return ticketMapper.toResponse(savedTicket)
    }

    @Transactional
    override fun changeStatus(ticketNumber: Int, status: String): TicketResponse {
        val ticket = getOrThrow(ticketNumber)
        val status = QueueTicketStatus.valueOf(status.uppercase(Locale.getDefault()))
        ticket.status = status
        val savedTicket = ticketRepository.save(ticket)
        return ticketMapper.toResponse(savedTicket)
    }

    @Transactional
    override fun getTicketByTicketNumber(ticketNumber: Int): QueueTicket {
        return getOrThrow(ticketNumber)
    }

    private fun getOrThrow(ticketNumber: Int): QueueTicket {
        val queueTicket = ticketRepository.findByTicketNumber(ticketNumber)
        if (queueTicket.isPresent) {
            return queueTicket.get()
        } else {
            throw TicketNotFoundException("Ticket with number $ticketNumber not found")
        }
    }

}