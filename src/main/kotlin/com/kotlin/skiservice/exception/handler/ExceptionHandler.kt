package com.kotlin.skiservice.exception.handler

import com.kotlin.skiservice.exception.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(
        ClientNotFoundException::class,
        EquipmentNotFoundException::class,
        RentalOrderItemNotFoundException::class,
        EquipmentNotFoundException::class,
        EquipmentTypeNotFoundException::class,
        TicketNotFoundException::class,
        UserNotFoundException::class,
        ListNotFoundException::class,
        CellNotFoundException::class)
    fun handleNotFoundException(e: RuntimeException): ResponseEntity<ApplicationError> {
        val errorMessage = e.localizedMessage
        return ResponseEntity(ApplicationError(errorMessage, "NOT_FOUND"), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(
        EquipmentAlreadyExistException::class,
        QueueException::class,
        EquipmentAlreadyInUseException::class,
        TooMuchEquipmentPerOneRentalOrderException::class,
        UserAlreadyExistException::class,
        RentalOrderHaveEquipmentInUseException::class,
        CellWithThisNumberAlreadyExistException::class,
        ClientWithTheSameTicketAlreadyExistException::class)
    fun handleConflictException(e: RuntimeException): ResponseEntity<ApplicationError> {
        val errorMessage = e.localizedMessage
        return ResponseEntity(ApplicationError(errorMessage, "CONFLICT"), HttpStatus.CONFLICT)
    }
}