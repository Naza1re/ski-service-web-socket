package com.kotlin.skiservice.entities.status

enum class QueueTicketStatus(
    val value: String
) {
    IN_QUEUE("queue"),
    REGISTRATION("registration"),
    REGISTRATION_COMPLETED("registration-completed"),
    RENTAL_ORDER("rental"),
    RENTAL_ORDER_COMPLETED("rental-completed"),
    PAYMENT("payment"),
    PROCEED("proceed");

    companion object {
        fun fromValue(value: String): QueueTicketStatus? {
            return entries.firstOrNull { it.value == value }
        }
    }
}