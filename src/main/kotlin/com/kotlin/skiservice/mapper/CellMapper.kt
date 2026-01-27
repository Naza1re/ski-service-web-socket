package com.kotlin.skiservice.mapper

import com.kotlin.skiservice.dto.cell.CellResponse
import com.kotlin.skiservice.entities.Cell
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface CellMapper {

    fun toResponse(cell: Cell): CellResponse

    fun toResponseList(cells: List<Cell>): List<CellResponse>
}