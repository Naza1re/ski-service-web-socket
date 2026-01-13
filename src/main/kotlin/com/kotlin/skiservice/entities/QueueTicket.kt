package com.kotlin.skiservice.entities

import jakarta.persistence.*

import jakarta.persistence.*

@Entity
@Table(name = "queue_ticket")
class QueueTicket(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "ticket_number", nullable = false)
    val ticketNumber: Int,

    @Column(nullable = false)
    val status: String
)
