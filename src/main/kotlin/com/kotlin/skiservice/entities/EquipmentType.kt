package com.kotlin.skiservice.entities

import jakarta.persistence.*

@Entity
@Table(name = "equipment_type")
class EquipmentType(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val name: String
)
