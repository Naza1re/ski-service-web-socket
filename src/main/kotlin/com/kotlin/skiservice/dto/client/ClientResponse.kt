package com.kotlin.skiservice.dto.client

import com.kotlin.skiservice.entities.enums.Skill

data class ClientResponse (
    val id: Long,
    val fullName: String,
    val height: Int,
    val weight: Int,
    val shoeSize: Int,
    val phoneNumber: String,
    val skillLevel: Skill,
)