package com.kotlin.skiservice.repository

import com.kotlin.skiservice.entities.QueueTicket
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface TicketRepository : JpaRepository<QueueTicket, Long> {

    @Query("SELECT MAX(t.ticketNumber) FROM QueueTicket t")
    fun findMaxTicketNumber(): Int?

    @Query("SELECT q FROM QueueTicket q WHERE q.status = 'IN_QUEUE' ORDER BY q.ticketNumber ASC")
    fun findNextWaitingTicket(pageable: PageRequest): QueueTicket?

    @Query("SELECT q FROM QueueTicket q WHERE q.status = 'IN_PROCESS' ORDER BY q.ticketNumber DESC")
    fun findCurrentTicket(pageable: PageRequest): QueueTicket?

    fun findByTicketNumber(ticketNumber: Int): Optional<QueueTicket>
}