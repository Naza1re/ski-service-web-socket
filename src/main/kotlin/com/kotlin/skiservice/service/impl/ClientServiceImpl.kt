package com.kotlin.skiservice.service.impl

import com.kotlin.skiservice.dto.client.ClientRequest
import com.kotlin.skiservice.dto.client.ClientResponse
import com.kotlin.skiservice.entities.Client
import com.kotlin.skiservice.exception.ClientNotFoundException
import com.kotlin.skiservice.mapper.ClientMapper
import com.kotlin.skiservice.queue.service.QueueService
import com.kotlin.skiservice.repository.ClientRepository
import com.kotlin.skiservice.service.ClientService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ClientServiceImpl(
    private val clientRepository : ClientRepository,
    private val clientMapper: ClientMapper,
    private val queueService: QueueService,
) : ClientService {

    @Transactional(readOnly = true)
    override fun getClient(id: Long): ClientResponse {
        val client = getOrThrow(id)
        return clientMapper.toResponse(client)
    }

    @Transactional
    override fun createClient(clientRequest: ClientRequest): ClientResponse {
        val queueTicket = queueService.getByTicketNumber(clientRequest.ticketNumber)
        val clientToSave = clientMapper.toModel(clientRequest)
        clientToSave.queueTicket = queueTicket
        val savedClient = clientRepository.save(clientToSave)
        return clientMapper.toResponse(savedClient)
    }

    @Transactional
    override fun deleteClient(id: Long): ClientResponse {
        val clientToDelete = getOrThrow(id)
        clientRepository.delete(clientToDelete)
        return clientMapper.toResponse(clientToDelete)
    }

    @Transactional
    override fun updateClient(id: Long, clientRequest: ClientRequest): ClientResponse {
        val client = getOrThrow(id)
        val updatedClient = clientMapper.toModel(clientRequest)
        updatedClient.id = client.id
        val savedClient = clientRepository.save(updatedClient)
        return clientMapper.toResponse(savedClient)
    }

    @Transactional(readOnly = true)
    override fun getClientByTicketNumber(number: Long): Client {
        val clientByTicket = clientRepository.findByQueueTicket(number)
        if (clientByTicket.isPresent) {
            return clientByTicket.get()
        } else {
            throw ClientNotFoundException("Client with ticket number $number not found")
        }
    }

    private fun getOrThrow(id: Long): Client {
        return clientRepository.findById(id).orElseThrow {
           ClientNotFoundException("Client was not found by id $id")
        }
    }
}