package com.kotlin.skiservice.entities.status

enum class QueueTicketStatus(
    val value: String
) {
    IN_QUEUE("query"),
    REGISTRATION("registration"),
    RENTAL_ORDER("rental"),
    PAYMENT("payment"),
    PROCEED("proceed");

    companion object {
        fun fromValue(value: String): QueueTicketStatus? {
            return entries.firstOrNull { it.value == value }
        }
    }
}