package com.kotlin.skiservice.entities

import jakarta.persistence.*

@Entity
@Table(name = "rental_order_item")
class RentalOrderItem(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_order_id", nullable = false)
    val rentalOrder: RentalOrder,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false)
    val equipment: Equipment
)
