package com.kotlin.skiservice.repository

import com.kotlin.skiservice.entities.EquipmentType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface EquipmentTypeRepository : JpaRepository<EquipmentType, Long>, JpaSpecificationExecutor<EquipmentType> {
    fun findByCode(code: String): Optional<EquipmentType>
}