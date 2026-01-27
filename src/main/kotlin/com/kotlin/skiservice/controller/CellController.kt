package com.kotlin.skiservice.controller

import com.kotlin.skiservice.dto.cell.CellListResponse
import com.kotlin.skiservice.dto.cell.CellResponse
import com.kotlin.skiservice.dto.cell.CreateCellRequest
import com.kotlin.skiservice.service.CellService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v0.1/cells")
//@PreAuthorize("hasAnyRole('RENTAL_MANAGER','ADMIN')")
class CellController(
    private val cellService: CellService
) {

    @Operation(summary = "Создать ячейку для хранения залогового документа аренды")
    @PostMapping
    fun create(@RequestBody createCellRequest: CreateCellRequest): ResponseEntity<CellResponse> {
        return ResponseEntity.ok(cellService.createCell(createCellRequest))
    }

    @Operation(summary = "Получить все ячейки для хранения залогового документа аренды")
    @GetMapping
    fun get() : ResponseEntity<CellListResponse> {
        return ResponseEntity.ok(cellService.getAllCells())
    }

    @Operation(summary = "Удалить ячейку для хранения залогового документа аренды")
    @DeleteMapping("/{cellNumber}")
    fun delete(@PathVariable("cellNumber") cellNumber : String) : ResponseEntity<Void> {
        cellService.deleteCell(cellNumber)
        return ResponseEntity.noContent().build()
    }

    @Operation(summary = "Забронировать ячейку для хранения залогового документа аренды")
    @PutMapping("/{cellNumber}/reserve")
    fun update(@PathVariable("cellNumber") cellNumber : String) : ResponseEntity<CellResponse> {
        return ResponseEntity.ok(cellService.bookCell(cellNumber))
    }

}
