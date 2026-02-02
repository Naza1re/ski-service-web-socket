package com.kotlin.skiservice.repository

import com.kotlin.skiservice.entities.SkiPass
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SkiPassRepository : JpaRepository<SkiPass, Long> {
    fun findByBarCode(barcode: String): Optional<SkiPass>
}