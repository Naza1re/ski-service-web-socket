package com.kotlin.skiservice.service.impl

import com.kotlin.skiservice.dto.tariff.TariffRequest
import com.kotlin.skiservice.dto.tariff.TariffResponse
import com.kotlin.skiservice.entities.Tariff
import com.kotlin.skiservice.exception.TariffNotFoundException
import com.kotlin.skiservice.mapper.TariffMapper
import com.kotlin.skiservice.repository.TariffRepository
import com.kotlin.skiservice.service.TariffService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TariffServiceImpl(
    private val tariffRepository: TariffRepository,
    private val tariffMapper: TariffMapper
) : TariffService {

    @Transactional(readOnly = true)
    override fun getTariffByCode(code: String): Tariff {
        return getOrThrow(code)
    }

    @Transactional(readOnly = true)
    override fun getByCode(tariffCode: String): TariffResponse {
        return tariffMapper.toResponse(getOrThrow(tariffCode))
    }

    @Transactional(readOnly = true)
    override fun getAllTariff(page: Int, size: Int): Page<Tariff> {
        val pageRequest = PageRequest.of(page, size)
        return tariffRepository.findAll(pageRequest)
    }

    @Transactional
    override fun createTariff(tariffRequest: TariffRequest): TariffResponse {
        validateTariff(tariffRequest)
        val tariffToSave = Tariff(
            code = tariffRequest.code,
            name = tariffRequest.name,
            description = tariffRequest.description,
            startTime = tariffRequest.startTime,
            hours = tariffRequest.hours,
        )
        val savedTariff = tariffRepository.save(tariffToSave)
        return tariffMapper.toResponse(savedTariff)
    }

    @Transactional
    override fun updateTariff(tariffCode: String, tariffRequest: TariffRequest): TariffResponse {
        val tariff = getOrThrow(tariffCode)
        validateTariff(tariffRequest)
        val tariffToSave = Tariff(
            id = tariff.id,
            code = tariffRequest.code,
            name = tariffRequest.name,
            description = tariffRequest.description,
            startTime = tariffRequest.startTime,
            hours = tariffRequest.hours,
        )
        val savedTariff = tariffRepository.save(tariffToSave)
        return tariffMapper.toResponse(savedTariff)
    }

    @Transactional
    override fun deleteTariff(tariffCode: String) {
        val tariff = getOrThrow(tariffCode)
        tariffRepository.delete(tariff)
    }

    private fun validateTariff(tariff: TariffRequest) {
        val tariffByCode = tariffRepository.findByCode(tariff.code)
        if (tariffByCode.isPresent) {
            throw TariffNotFoundException("Tariff with code ${tariff.code} already exist")
        }
    }

    private fun getOrThrow(code: String) : Tariff {
        val tariff = tariffRepository.findByCode(code)
        if (tariff.isPresent) {
            return tariff.get()
        } else {
            throw TariffNotFoundException("Tariff with code $code not found")
        }
    }
}