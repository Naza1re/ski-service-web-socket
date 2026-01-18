package com.kotlin.skiservice.repository

import com.kotlin.skiservice.entities.Equipment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface EquipmentRepository : JpaRepository<Equipment, Int> {
    fun findByBarCode(barCode: String): Optional<Equipment>
}