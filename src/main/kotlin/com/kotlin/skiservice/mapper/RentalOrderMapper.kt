package com.kotlin.skiservice.mapper

import com.kotlin.skiservice.dto.rental.RentalOrderResponse
import com.kotlin.skiservice.entities.RentalOrder
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface RentalOrderMapper {

    @Mapping(source = "client.fullName", target = "clientName")
    @Mapping(source = "client.phoneNumber", target = "clientPhoneNumber")
    fun toResponse(rentalOrder: RentalOrder) : RentalOrderResponse

    @Mapping(source = "client.fullName", target = "clientName")
    @Mapping(source = "client.phoneNumber", target = "clientPhoneNumber")
    @Mapping(source = "cell.cellNumber", target = "help")
    fun toEndResponse(rentalOrder: RentalOrder) : RentalOrderResponse

}