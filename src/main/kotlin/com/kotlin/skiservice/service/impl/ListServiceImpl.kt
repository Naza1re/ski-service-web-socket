package com.kotlin.skiservice.service.impl

import com.kotlin.skiservice.dto.list.ListItemResponse
import com.kotlin.skiservice.exception.ListNotFoundException
import com.kotlin.skiservice.list.ListProvider
import com.kotlin.skiservice.service.ListService
import org.springframework.stereotype.Service

@Service
class ListServiceImpl(
    private val listOfListProvider: List<ListProvider<*>>
) : ListService {

    override fun getListItemsByListName(listName: String): ListItemResponse {
        var listProvider: ListProvider<*>? = null
        for (listItem in listOfListProvider) {
            if (listItem.match(listName)) {
                listProvider = listItem
            }
        }

        if (listProvider != null) {
            return ListItemResponse(listName, listProvider.getList())
        } else {
            throw ListNotFoundException("List with name $listName not found")
        }

    }
}
