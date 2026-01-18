package com.kotlin.skiservice.repository

import com.kotlin.skiservice.entities.RentalOrder
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RentalRepository :JpaRepository<RentalOrder, Long> {
}