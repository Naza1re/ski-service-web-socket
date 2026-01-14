package com.kotlin.skiservice.mapper

import com.kotlin.skiservice.dto.client.ClientRequest
import com.kotlin.skiservice.dto.client.ClientResponse
import com.kotlin.skiservice.entities.Client
import org.mapstruct.Mapper
import org.springframework.stereotype.Component

@Mapper(componentModel = "spring")
@Component
interface ClientMapper {

    fun toResponse(client: Client): ClientResponse
    fun toModel(clientRequest: ClientRequest): Client
}
