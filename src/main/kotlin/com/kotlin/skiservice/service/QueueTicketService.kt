package com.kotlin.skiservice.service

import com.kotlin.skiservice.dto.ticket.TicketResponse
import com.kotlin.skiservice.entities.QueueTicket

interface QueueTicketService {
    fun getNewTicket(): TicketResponse
    fun changeStatus(ticketNumber: Int, status: String): TicketResponse
    fun getTicketByTicketNumber(ticketNumber: Int): QueueTicket
}