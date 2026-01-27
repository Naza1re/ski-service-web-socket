package com.kotlin.skiservice.service.impl

import com.kotlin.skiservice.config.properties.SecurityProperties
import com.kotlin.skiservice.service.JwtService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtServiceImpl(
    private val securityProperties: SecurityProperties,
) : JwtService {

    override fun generateToken(username: UserDetails): String =
        Jwts.builder()
            .setSubject(username.username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 60 * 60 * 1000))
            .signWith(Keys.hmacShaKeyFor(securityProperties.secret.toByteArray()), SignatureAlgorithm.HS256)
            .compact()

    override fun extractUsername(token: String): String =
        Jwts.parserBuilder()
            .setSigningKey(securityProperties.secret.toByteArray())
            .build()
            .parseClaimsJws(token)
            .body
            .subject
}
