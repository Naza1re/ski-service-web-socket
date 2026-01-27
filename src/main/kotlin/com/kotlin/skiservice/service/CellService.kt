package com.kotlin.skiservice.service

import com.kotlin.skiservice.dto.cell.CellListResponse
import com.kotlin.skiservice.dto.cell.CellResponse
import com.kotlin.skiservice.dto.cell.CreateCellRequest
import com.kotlin.skiservice.entities.Cell

interface CellService {
    fun getAllCells(): CellListResponse
    fun createCell(createCellRequest: CreateCellRequest): CellResponse
    fun deleteCell(cellNumber: String)
    fun bookCell(cellNumber: String): CellResponse
    fun getCell(cellNumber: String): Cell
}