package com.kotlin.skiservice.entities

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "tariff_equipment_type_matrix")
class TariffEquipmentTypeMatrix(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tariff_id", nullable = false)
    val tariff: Tariff,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_type_id", nullable = false)
    val equipmentType: EquipmentType,

    @Column(name = "price_per_hour", nullable = false)
    var pricePerHour: BigDecimal
)