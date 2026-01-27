package com.kotlin.skiservice.entities

import com.kotlin.skiservice.entities.status.EquipmentStatus
import jakarta.persistence.*

@Entity
@Table(name = "equipment")
class Equipment(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, unique = true)
    val barCode: String,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "equipment_type_id", nullable = false)
    var type: EquipmentType,

    val size: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: EquipmentStatus = EquipmentStatus.NOT_IN_USE

)
