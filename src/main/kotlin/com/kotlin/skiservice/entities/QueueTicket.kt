package com.kotlin.skiservice.entities

import com.kotlin.skiservice.entities.status.QueueTicketStatus
import jakarta.persistence.*

@Entity
@Table(name = "queue_ticket")
class QueueTicket(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "ticket_number", nullable = false)
    val ticketNumber: Int,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: QueueTicketStatus
)
