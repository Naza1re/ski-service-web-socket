package com.kotlin.skiservice.entities

import com.kotlin.skiservice.entities.status.RentalOrderStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "rental_order")
class RentalOrder(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    val client: Client,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val status: RentalOrderStatus,
)
