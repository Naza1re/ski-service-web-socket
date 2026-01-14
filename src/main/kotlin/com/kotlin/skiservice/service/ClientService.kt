package com.kotlin.skiservice.service

import com.kotlin.skiservice.dto.client.ClientRequest
import com.kotlin.skiservice.dto.client.ClientResponse

interface ClientService {
    fun getClient(id: Long) : ClientResponse
    fun createClient(clientRequest: ClientRequest): ClientResponse
    fun deleteClient(id: Long): ClientResponse
    fun updateClient(id: Long, clientRequest: ClientRequest): ClientResponse
}