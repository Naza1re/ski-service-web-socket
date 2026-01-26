package com.kotlin.skiservice.controller

import com.kotlin.skiservice.dto.client.ClientRequest
import com.kotlin.skiservice.dto.client.ClientResponse
import com.kotlin.skiservice.service.ClientService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v0.1/clients")
//@PreAuthorize("hasAnyRole('REGISTRATION_MANAGER','ADMIN')")
class ClientController(
    private val clientService: ClientService
) {
    @Operation(summary = "Получить пользователя. Доступно для ролей (REGISTRATION_MANAGER,ADMIN)")
    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: Long): ResponseEntity<ClientResponse> {
        return ResponseEntity.ok(clientService.getClient(id))
    }

    @Operation(summary = "Создать пользователя. Доступно для ролей (REGISTRATION_MANAGER,ADMIN)")
    @PostMapping
    fun create(@RequestBody clientRequest: ClientRequest): ResponseEntity<ClientResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.createClient(clientRequest))
    }

    @Operation(summary = "Удалить пользователя. Доступно для ролей (REGISTRATION_MANAGER,ADMIN) ")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Long): ResponseEntity<ClientResponse> {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(clientService.deleteClient(id))
    }

    @Operation(summary = "Редактировать пользователя. Доступно для ролей (REGISTRATION_MANAGER,ADMIN)")
    @PatchMapping("/{id}")
    fun update(@PathVariable("id") id: Long, clientRequest: ClientRequest) : ResponseEntity<ClientResponse> {
        return ResponseEntity.ok(clientService.updateClient(id, clientRequest))
    }

}
