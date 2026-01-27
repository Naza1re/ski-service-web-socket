package com.kotlin.skiservice.service

import com.kotlin.skiservice.dto.list.ListItemResponse

interface ListService {
    fun getListItemsByListName(listName: String): ListItemResponse
}