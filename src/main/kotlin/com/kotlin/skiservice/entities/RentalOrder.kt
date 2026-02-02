package com.kotlin.skiservice.entities

import com.kotlin.skiservice.entities.enums.DocumentType
import com.kotlin.skiservice.entities.status.RentalOrderStatus
import jakarta.persistence.*
import java.math.BigDecimal
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

    @Column(name = "start_at",)
    var startAt: LocalDateTime? = null,

    @Column(name = "end_at")
    var endAt: LocalDateTime? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cell_id")
    var cell: Cell? = null,

    @Column(name = "price")
    var price: BigDecimal = BigDecimal.ZERO,

    @Column(name = "document_number")
    val documentNumber: String,

    @Column(name = "document_type")
    @Enumerated(EnumType.STRING)
    val documentType: DocumentType,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: RentalOrderStatus,
)
