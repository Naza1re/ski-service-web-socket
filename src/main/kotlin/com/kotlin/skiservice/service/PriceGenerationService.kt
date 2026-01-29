package com.kotlin.skiservice.service

import com.kotlin.skiservice.dto.price.PriceResponse

interface PriceGenerationService {
    fun generatePriceForTariff(ticketNumber: Int, tariffCode: String, countHours: Int): PriceResponse
}