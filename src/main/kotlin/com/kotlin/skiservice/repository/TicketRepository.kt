package com.kotlin.skiservice.repository

import com.kotlin.skiservice.entities.QueueTicket
import com.kotlin.skiservice.entities.status.QueueTicketStatus
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface TicketRepository : JpaRepository<QueueTicket, Long> {

    @Query("SELECT MAX(t.ticketNumber) FROM QueueTicket t")
    fun findMaxTicketNumber(): Int?

    fun findFirstByStatusOrderByTicketNumberAsc(
        status: QueueTicketStatus
    ): QueueTicket?

    fun findFirstByStatusOrderByTicketNumberDesc(
        status: QueueTicketStatus
    ): QueueTicket?

    fun findByTicketNumber(ticketNumber: Int): Optional<QueueTicket>
}