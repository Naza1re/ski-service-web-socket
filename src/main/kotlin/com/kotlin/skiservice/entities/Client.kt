package com.kotlin.skiservice.entities

import com.kotlin.skiservice.entities.enums.Skill
import jakarta.persistence.*

@Entity
@Table(name = "client")
class Client(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val fullName: String,
    val height: Int,
    val weight: Int,
    val shoeSize: Int,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val skillLevel: Skill,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "queue_ticket_id")
    var queueTicket: QueueTicket?
) {
}