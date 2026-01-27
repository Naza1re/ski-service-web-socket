package com.kotlin.skiservice.list.provider

import com.kotlin.skiservice.dto.list.ListItemResponseValue
import com.kotlin.skiservice.entities.enums.DocumentType
import com.kotlin.skiservice.list.ListProvider
import org.springframework.stereotype.Service

@Service
class DocumentListProvider : ListProvider<DocumentType> {

    companion object {
        const val DOCUMENT_LIST = "document"
    }

    override fun match(string: String): Boolean {
        return string == DOCUMENT_LIST
    }

    override fun getList(): List<ListItemResponseValue> {
        return DocumentType.entries.map { item ->
            ListItemResponseValue(item.value, item.name)
        }
    }

}