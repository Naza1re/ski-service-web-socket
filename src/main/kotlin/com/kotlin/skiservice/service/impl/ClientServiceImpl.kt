package com.kotlin.skiservice.service.impl

import com.kotlin.skiservice.dto.client.ClientRequest
import com.kotlin.skiservice.dto.client.ClientResponse
import com.kotlin.skiservice.entities.Client
import com.kotlin.skiservice.exception.ClientNotFoundException
import com.kotlin.skiservice.exception.ClientWithTheSameTicketAlreadyExistException
import com.kotlin.skiservice.mapper.ClientMapper
import com.kotlin.skiservice.queue.service.QueueService
import com.kotlin.skiservice.repository.ClientRepository
import com.kotlin.skiservice.service.ClientService
import com.kotlin.skiservice.service.QueueTicketService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ClientServiceImpl(
    private val clientRepository : ClientRepository,
    private val clientMapper: ClientMapper,
    private val queueTicketService: QueueTicketService
) : ClientService {

    @Transactional(readOnly = true)
    override fun getClient(id: Long): ClientResponse {
        val client = getOrThrow(id)
        return clientMapper.toResponse(client)
    }

    @Transactional
    override fun createClient(clientRequest: ClientRequest): ClientResponse {
        validateCreateClient(clientRequest)
        val queueTicket = queueTicketService.getTicketByTicketNumber(clientRequest.ticketNumber)
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
    override fun getClientByTicketNumber(number: Int): Client {
        val clientByTicket = clientRepository.findByQueueTicket(number)
        if (clientByTicket.isPresent) {
            return clientByTicket.get()
        } else {
            throw ClientNotFoundException("Client with ticket number $number not found")
        }
    }

    private fun validateCreateClient(clientRequest: ClientRequest) {
        val client = clientRepository.findByQueueTicket(clientRequest.ticketNumber)
        if (client.isPresent) {
            throw ClientWithTheSameTicketAlreadyExistException("Client with ticker number ${clientRequest.ticketNumber} already exists")
        }
    }

    private fun getOrThrow(id: Long): Client {
        return clientRepository.findById(id).orElseThrow {
           ClientNotFoundException("Client was not found by id $id")
        }
    }
}