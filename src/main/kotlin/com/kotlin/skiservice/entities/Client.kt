package com.kotlin.skiservice.entities

import jakarta.persistence.*

@Entity
@Table(name = "client")
class Client(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null,
    private val fullName: String,
    private val height: Int,
    private val weight: Int,
    private val shoeSize: Int,
    private val skillLevel: String,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "queue_ticket_id")
    val queueTicket: QueueTicket?
) {
}