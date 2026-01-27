package com.kotlin.skiservice.entities


import com.kotlin.skiservice.entities.status.CellStatus
import jakarta.persistence.*

@Entity
@Table(name = "cell")
class Cell(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "cell_number", nullable = false, unique = true)
    val cellNumber: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: CellStatus,

    @OneToOne(mappedBy = "cell")
    var rentalOrder: RentalOrder? = null
)