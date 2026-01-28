package com.kotlin.skiservice.service

import com.kotlin.skiservice.dto.tariff.TariffRequest
import com.kotlin.skiservice.dto.tariff.TariffResponse
import com.kotlin.skiservice.entities.Tariff
import org.springframework.data.domain.Page

interface TariffService {
    fun getTariffByCode(code: String): Tariff
    fun getByCode(tariffCode: String): TariffResponse
    fun getAllTariff(page: Int, size: Int): Page<Tariff>
    fun createTariff(tariffRequest: TariffRequest): TariffResponse
    fun updateTariff(tariffCode: String, tariffRequest: TariffRequest): TariffResponse
    fun deleteTariff(tariffCode: String)
}