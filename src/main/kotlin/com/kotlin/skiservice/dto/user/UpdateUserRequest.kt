package com.kotlin.skiservice.dto.user

import com.kotlin.skiservice.entities.user.role.Role

data class UpdateUserRequest(
    val roles: MutableSet<Role>,
)
