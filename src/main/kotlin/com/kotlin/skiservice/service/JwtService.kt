package com.kotlin.skiservice.service

import org.springframework.security.core.userdetails.UserDetails

interface JwtService {
    fun generateToken(username: UserDetails): String
    fun extractUsername(token: String): String
}