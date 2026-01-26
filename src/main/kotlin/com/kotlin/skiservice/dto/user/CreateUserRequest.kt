package com.kotlin.skiservice.dto.user

import com.kotlin.skiservice.entities.user.role.Role

data class CreateUserRequest(
    val username: String,
    val password: String,
    val roles: Set<Role>
)