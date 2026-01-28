package com.kotlin.skiservice.mapper

import com.kotlin.skiservice.dto.skipass.SkiPassResponse
import com.kotlin.skiservice.entities.SkiPass
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface SkiPassMapper {
    fun toResponse(skiPass: SkiPass): SkiPassResponse
}