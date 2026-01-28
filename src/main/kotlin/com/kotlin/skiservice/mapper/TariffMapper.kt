package com.kotlin.skiservice.mapper

import com.kotlin.skiservice.dto.tariff.TariffResponse
import com.kotlin.skiservice.entities.Tariff
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface TariffMapper {

    fun toResponse(tariff: Tariff) : TariffResponse
}