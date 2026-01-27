package com.kotlin.skiservice.controller

import com.kotlin.skiservice.dto.list.ListItemResponse
import com.kotlin.skiservice.service.ListService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v0.1/list")
class ListController(
    private val listService: ListService
) {

    @Operation(summary = "Получение списка элементов (выпадашки для фронта) Например: role, skill, document")
    @GetMapping("/{listName}")
    fun getList(@PathVariable("listName") listName: String) : ResponseEntity<ListItemResponse> {
        return ResponseEntity.ok(listService.getListItemsByListName(listName))
    }
}