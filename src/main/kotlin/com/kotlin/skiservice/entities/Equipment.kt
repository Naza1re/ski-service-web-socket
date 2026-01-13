package com.kotlin.skiservice.entities

import jakarta.persistence.*

@Entity
@Table(name = "equipment")
class Equipment(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val barcode: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_type_id", nullable = false)
    val type: EquipmentType,

    val size: String?,

    @Column(nullable = false)
    val status: String
)
