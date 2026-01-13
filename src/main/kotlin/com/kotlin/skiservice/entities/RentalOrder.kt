package com.kotlin.skiservice.entities

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "rental_order")
class RentalOrder(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    val client: Client,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    val status: String // OPEN, CLOSED
)
