package com.kotlin.skiservice.entities

import jakarta.persistence.*

@Entity
@Table(name = "client")
class Client(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val fullName: String,
    val height: Int,
    val weight: Int,
    val shoeSize: Int,
    val skillLevel: String,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "queue_ticket_id")
    var queueTicket: QueueTicket?
) {
}