package com.kotlin.skiservice.service.impl

import com.kotlin.skiservice.dto.ticket.TicketResponse
import com.kotlin.skiservice.entities.QueueTicket
import com.kotlin.skiservice.entities.status.QueueTicketStatus.IN_QUEUE
import com.kotlin.skiservice.mapper.QueueTicketMapper
import com.kotlin.skiservice.repository.TicketRepository
import com.kotlin.skiservice.service.TicketService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TicketServiceImpl(
    private val ticketRepository: TicketRepository,
    private val ticketMapper: QueueTicketMapper
) : TicketService {

    @Transactional
    override fun getNewTicket(): TicketResponse {
        val maxTicketNumber = ticketRepository.findMaxTicketNumber() ?: 0
        val ticket = QueueTicket(ticketNumber = maxTicketNumber + 1, status = IN_QUEUE)
        val savedTicket = ticketRepository.save(ticket)
        return ticketMapper.toResponse(savedTicket)
    }

}