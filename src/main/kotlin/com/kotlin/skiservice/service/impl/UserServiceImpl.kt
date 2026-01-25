package com.kotlin.skiservice.service.impl

import com.kotlin.skiservice.dto.user.CreateUserRequest
import com.kotlin.skiservice.dto.user.UserResponse
import com.kotlin.skiservice.entities.user.User
import com.kotlin.skiservice.entities.user.role.Role
import com.kotlin.skiservice.exception.UserAlreadyExistException
import com.kotlin.skiservice.mapper.UserMapper
import com.kotlin.skiservice.repository.UserRepository
import com.kotlin.skiservice.service.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val userMapper: UserMapper
) : UserService {

    override fun createUser(createUserRequest: CreateUserRequest): UserResponse {
        validateCreateUser(createUserRequest)

        val user = User(
            username = createUserRequest.username,
            password = passwordEncoder.encode(createUserRequest.password),
            roles = createUserRequest.roles
        )
        val savedUser = userRepository.save(user)

        return userMapper.toResponse(savedUser)
    }

    override fun getUsers(page: Int, size: Int): Page<User> {
        val pageRequest = PageRequest.of(page, size)
        val users = userRepository.findAll(pageRequest)
        return users
    }

    override fun getRoles(): List<Role> {
        val roles = Role.entries.toTypedArray().toList()
        return roles
    }

    private fun validateCreateUser(createUserRequest: CreateUserRequest) {
        val user = userRepository.findByUsername(createUserRequest.username)
        if (user != null) {
            throw UserAlreadyExistException("User with username ${createUserRequest.username} already exists")
        }
    }

}