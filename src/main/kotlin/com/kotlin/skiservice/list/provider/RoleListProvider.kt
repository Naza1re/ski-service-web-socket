package com.kotlin.skiservice.list.provider

import com.kotlin.skiservice.dto.list.ListItemResponseValue
import com.kotlin.skiservice.entities.user.role.Role
import com.kotlin.skiservice.list.ListProvider
import org.springframework.stereotype.Service

@Service
class RoleListProvider : ListProvider<Role> {

    companion object {
        const val ROLE_LIST = "role"
    }

    override fun match(string: String): Boolean {
        return string == ROLE_LIST
    }

    override fun getList(): List<ListItemResponseValue> {
        return Role.entries.map { item ->
            ListItemResponseValue(item.roleName, item.name)
        }
    }
}