package com.kotlin.skiservice.repository

import com.kotlin.skiservice.entities.RentalOrderItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface RentalOrderItemRepository : JpaRepository<RentalOrderItem, Long>, JpaSpecificationExecutor<RentalOrderItem> {
    fun findAllByRentalOrderId(rentalOrderId: Long): List<RentalOrderItem>
}