package com.kotlin.skiservice.repository

import com.kotlin.skiservice.entities.Tariff
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TariffRepository : JpaRepository<Tariff, Long> {
    fun findByCode(code: String): Optional<Tariff>
}