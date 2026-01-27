package com.kotlin.skiservice.dto.list

data class ListItemResponse(
    val listName: String,
    val list: List<ListItemResponseValue>,
)