package com.kotlin.skiservice.repository

import com.kotlin.skiservice.entities.Cell
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface CellRepository : JpaRepository<Cell, Long> {

    fun findByCellNumber(number: String): Optional<Cell>
}