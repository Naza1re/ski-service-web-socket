package com.kotlin.skiservice.dto.client

data class ClientRequest(
    val fullName: String,
    val height: Int,
    val weight: Int,
    val shoeSize: Int,
    val skillLevel: String,
    val ticketNumber: Int
)