package com.kotlin.skiservice.list

import com.kotlin.skiservice.dto.list.ListItemResponseValue

interface ListProvider<T> {
    fun match(string: String): Boolean
    fun getList(): List<ListItemResponseValue>
}