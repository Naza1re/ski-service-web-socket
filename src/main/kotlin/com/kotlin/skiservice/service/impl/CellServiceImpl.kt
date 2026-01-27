package com.kotlin.skiservice.service.impl

import com.kotlin.skiservice.dto.cell.CellListResponse
import com.kotlin.skiservice.dto.cell.CellResponse
import com.kotlin.skiservice.dto.cell.CreateCellRequest
import com.kotlin.skiservice.entities.Cell
import com.kotlin.skiservice.entities.status.CellStatus
import com.kotlin.skiservice.exception.CellWithThisNumberAlreadyExistException
import com.kotlin.skiservice.mapper.CellMapper
import com.kotlin.skiservice.repository.CellRepository
import com.kotlin.skiservice.service.CellService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CellServiceImpl(
    private val cellRepository: CellRepository,
    private val cellMapper: CellMapper
) : CellService {

    @Transactional(readOnly = true)
    override fun getAllCells(): CellListResponse {
        val cells = cellRepository.findAll()
        return CellListResponse(cellMapper.toResponseList(cells))
    }

    @Transactional
    override fun createCell(createCellRequest: CreateCellRequest): CellResponse {
        validateCellNumber(createCellRequest.cellNumber)
        val cellToSave = Cell(
            id = null,
            cellNumber = createCellRequest.cellNumber,
            status = CellStatus.FREE
        )
        val savedCell = cellRepository.save(cellToSave)
        return cellMapper.toResponse(savedCell)
    }

    @Transactional
    override fun deleteCell(cellNumber: String) {
        val cell = getOrThrow(cellNumber)
        cellRepository.delete(cell)
    }

    @Transactional
    override fun bookCell(cellNumber: String): CellResponse {
        val cell = getOrThrow(cellNumber)
        cell.status = CellStatus.RESERVED
        val savedCell = cellRepository.save(cell)
        return cellMapper.toResponse(savedCell)
    }

    override fun getCell(cellNumber: String): Cell {
        return getOrThrow(cellNumber)
    }

    private fun validateCellNumber(cellNumber: String) {
        val number = cellRepository.findByCellNumber(cellNumber)
        if (number.isPresent) {
            throw CellWithThisNumberAlreadyExistException("Cell with number $cellNumber already exists")
        }
    }

    private fun getOrThrow(cellNumber: String): Cell {
        val cell = cellRepository.findByCellNumber(cellNumber)
        if (cell.isPresent) {
            return cell.get()
        } else {
            throw CellWithThisNumberAlreadyExistException("Cell with number $cellNumber already exists")
        }
    }
}