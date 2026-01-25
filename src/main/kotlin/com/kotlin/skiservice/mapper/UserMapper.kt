package com.kotlin.skiservice.mapper

import com.kotlin.skiservice.dto.user.UserResponse
import com.kotlin.skiservice.entities.user.User
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface UserMapper {
    fun toResponse(user: User): UserResponse
}