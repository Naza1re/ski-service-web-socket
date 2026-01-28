package com.kotlin.skiservice.repository

import com.kotlin.skiservice.entities.TariffEquipmentTypeMatrix
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TariffEquipmentTypeMatrixRepository : JpaRepository<TariffEquipmentTypeMatrix, Long> {
    fun findByTariffCodeAndEquipmentTypeCode(tariffCode: String, equipmentTypeCode: String): Optional<TariffEquipmentTypeMatrix>
}