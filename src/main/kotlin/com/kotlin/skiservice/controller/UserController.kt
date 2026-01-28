package com.kotlin.skiservice.controller

import com.kotlin.skiservice.dto.user.CreateUserRequest
import com.kotlin.skiservice.dto.user.UpdateUserRequest
import com.kotlin.skiservice.dto.user.UserResponse
import com.kotlin.skiservice.entities.user.User
import com.kotlin.skiservice.service.UserService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v0.1/users")
@PreAuthorize("hasAnyRole('USER_ADMIN', 'ADMIN')")
class UserController(
    private val userService: UserService
) {

    @Operation(summary = "Создать пользователя. Доступно для ролей (USER_ADMIN, ADMIN)")
    @PostMapping
    fun createUser(@RequestBody createUserRequest: CreateUserRequest): ResponseEntity<UserResponse> {
       return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(createUserRequest))
    }

    @Operation(summary = "Получить список всех пользователей. Доступно для ролей (USER_ADMIN, ADMIN)")
    @GetMapping
    fun getAllUsers(@RequestParam("page") page: Int, @RequestParam("size") size: Int): Page<User> {
        return userService.getUsers(page, size)
    }

    @Operation(summary = "Обновить роль пользователю. Доступно для ролей (USER_ADMIN, ADMIN)")
    @PatchMapping("/{userId}")
    fun updateUserRole(@RequestBody updateUserRequest: UpdateUserRequest, @PathVariable userId: Long): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.updateUserRequest(userId,updateUserRequest))
    }

}
