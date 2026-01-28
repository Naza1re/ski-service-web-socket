package com.kotlin.skiservice.mapper

import com.kotlin.skiservice.dto.tariff.matrix.TariffEquipmentTypeMatrixResponse
import com.kotlin.skiservice.entities.TariffEquipmentTypeMatrix
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface TariffEquipmentTypeMatrixMapper {

    fun toListResponse(listMatrix: List<TariffEquipmentTypeMatrix>): List<TariffEquipmentTypeMatrixResponse>

    @Mapping(source = "tariff.name", target = "tariff")
    @Mapping(source = "equipmentType.code", target = "equipmentType")
    fun toResponse(entity: TariffEquipmentTypeMatrix): TariffEquipmentTypeMatrixResponse
}