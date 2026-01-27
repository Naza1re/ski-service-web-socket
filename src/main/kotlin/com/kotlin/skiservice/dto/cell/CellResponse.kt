package com.kotlin.skiservice.dto.cell

import com.kotlin.skiservice.entities.status.CellStatus

data class CellResponse(
    val cellNumber: String,
    val status: CellStatus,
)