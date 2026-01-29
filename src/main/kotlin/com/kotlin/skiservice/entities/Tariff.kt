package com.kotlin.skiservice.entities

import jakarta.persistence.*
import java.time.LocalTime

@Entity
@Table(name = "tariff")
class Tariff(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "code")
    val code: String,

    @Column(name = "name")
    val name: String,

    @Column(name = "description")
    val description: String,

    @Column(name = "start_time", nullable = false)
    val startTime: LocalTime,

    @Column(nullable = false)
    val hours: Int,

)