package com.kotlin.skiservice.config.security

import com.kotlin.skiservice.filter.JwtFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig (
    private val jwtFilter: JwtFilter
) {

    companion object {
        const val AUTH_URL = "/api/v0.1/auth/**"
        const val SWAGGER_UI_URL = "/swagger-ui/**"
        const val SWAGGER_API_URL = "/v3/api-docs/**"
        const val QUEUE_URL = "/api/v0.1/queue/**"
        const val TICKET_URL = "/api/v0.1/ticket/**"
        const val CLIENT_URL = "/api/v0.1/clients/**"
        const val WEB_SOCKET = "/ws/**"
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOriginPatterns = listOf("*")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("*")
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .cors {  }
            .csrf { it.disable() }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests {
                it.requestMatchers(AUTH_URL).permitAll()

                it.requestMatchers(CLIENT_URL).permitAll()
                it.requestMatchers("/api/v0.1/users/**").permitAll() // Временно
                it.requestMatchers("/api/v0.1/list/**").permitAll()

                it.requestMatchers(SWAGGER_UI_URL, SWAGGER_API_URL).permitAll()
                it.requestMatchers(QUEUE_URL).permitAll()
                it.requestMatchers(TICKET_URL).permitAll()
                it.requestMatchers(CLIENT_URL).permitAll()


                it.requestMatchers(WEB_SOCKET).permitAll()

                it.anyRequest().authenticated()
            }
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager =
        config.authenticationManager
}