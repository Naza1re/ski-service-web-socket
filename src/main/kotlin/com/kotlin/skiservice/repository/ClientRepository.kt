package com.kotlin.skiservice.repository

import com.kotlin.skiservice.entities.Client
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ClientRepository : JpaRepository<Client, Long> {

    @Query("""
        SELECT c FROM Client c
        WHERE c.queueTicket.ticketNumber = :ticketNumber
    """)
    fun findByQueueTicket(ticketNumber: Int) : Optional<Client>
}