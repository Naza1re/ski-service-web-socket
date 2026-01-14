package com.kotlin.skiservice.repository

import com.kotlin.skiservice.entities.QueueTicket
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TicketRepository : JpaRepository<QueueTicket, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT q FROM QueueTicket q WHERE q.status = 'WAITING' ORDER BY q.ticketNumber ASC")
    fun findNextWaitingTickets(): List<QueueTicket>?

    @Query("SELECT q FROM QueueTicket q WHERE q.status = 'CALLED' ORDER BY q.ticketNumber DESC")
    fun findCurrentTicket(): QueueTicket?
}