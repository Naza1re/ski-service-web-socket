package com.kotlin.skiservice.controller

import com.kotlin.skiservice.dto.client.ClientRequest
import com.kotlin.skiservice.dto.client.ClientResponse
import com.kotlin.skiservice.service.ClientService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v0.1/clients")
class ClientController(
    private val clientService: ClientService
) {

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: Long): ResponseEntity<ClientResponse> {
        return ResponseEntity.ok(clientService.getClient(id))
    }

    @PostMapping
    fun create(@RequestBody clientRequest: ClientRequest): ResponseEntity<ClientResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.createClient(clientRequest))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Long): ResponseEntity<ClientResponse> {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(clientService.deleteClient(id))
    }

    @PatchMapping("/{id}")
    fun update(@PathVariable("id") id: Long, clientRequest: ClientRequest) : ResponseEntity<ClientResponse> {
        return ResponseEntity.ok(clientService.updateClient(id, clientRequest))
    }
}
