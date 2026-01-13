package com.kotlin.skiservice.entities

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "skipass")
class SkiPass(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val barcode: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    val client: Client,

    @Column(name = "valid_from", nullable = false)
    val validFrom: LocalDateTime,

    @Column(name = "valid_to", nullable = false)
    val validTo: LocalDateTime
)
