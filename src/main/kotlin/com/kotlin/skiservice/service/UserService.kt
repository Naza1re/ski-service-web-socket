package com.kotlin.skiservice.service

import com.kotlin.skiservice.dto.user.CreateUserRequest
import com.kotlin.skiservice.dto.user.UpdateUserRequest
import com.kotlin.skiservice.dto.user.UserResponse
import com.kotlin.skiservice.entities.user.User
import com.kotlin.skiservice.entities.user.role.Role
import org.springframework.data.domain.Page

interface UserService {
    fun createUser(createUserRequest: CreateUserRequest): UserResponse
    fun getUsers(page: Int, size: Int): Page<User>
    fun getRoles(): List<Role>
    fun updateUserRequest(userId: Long, updateUserRequest: UpdateUserRequest): UserResponse
}