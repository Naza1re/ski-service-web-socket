package com.kotlin.skiservice.exception.handler

import java.time.LocalDateTime

data class ApplicationError(
    val message: String,
    val status: String,
    val date: LocalDateTime = LocalDateTime.now(),
)