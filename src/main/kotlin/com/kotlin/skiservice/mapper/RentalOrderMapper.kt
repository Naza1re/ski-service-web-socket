package com.kotlin.skiservice.mapper

import com.kotlin.skiservice.dto.rental.RentalOrderResponse
import com.kotlin.skiservice.entities.RentalOrder
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface RentalOrderMapper {
    fun toResponse(rentalOrder: RentalOrder) : RentalOrderResponse

}