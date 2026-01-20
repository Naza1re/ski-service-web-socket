package com.kotlin.skiservice.service.impl

import com.kotlin.skiservice.config.properties.RentalItemProperties
import com.kotlin.skiservice.dto.rental.RentalOrderResponse
import com.kotlin.skiservice.dto.rental.item.RentalOrderItemListResponse
import com.kotlin.skiservice.dto.rental.item.RentalOrderItemRequest
import com.kotlin.skiservice.dto.rental.item.RentalOrderItemResponse
import com.kotlin.skiservice.entities.Equipment
import com.kotlin.skiservice.entities.RentalOrderItem
import com.kotlin.skiservice.entities.status.EquipmentStatus
import com.kotlin.skiservice.exception.EquipmentAlreadyInUseException
import com.kotlin.skiservice.exception.RentalOrderItemNotFoundException
import com.kotlin.skiservice.exception.TooMuchEquipmentPerOneRentalOrderException
import com.kotlin.skiservice.mapper.RentalOrderMapper
import com.kotlin.skiservice.repository.RentalOrderItemRepository
import com.kotlin.skiservice.service.EquipmentService
import com.kotlin.skiservice.service.RentalOrderItemService
import com.kotlin.skiservice.service.RentalService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RentalOrderItemServiceImpl(
    private val rentalOrderItemRepository: RentalOrderItemRepository,
    private val rentalOrderService: RentalService,
    private val equipmentService: EquipmentService,
    private val rentalOrderMapper: RentalOrderMapper,
    private val rentalItemProperties: RentalItemProperties
) : RentalOrderItemService {

    @Transactional
    override fun addItemToRentalOrder(rentalOrderId: Long, rentalOrderItemRequest: RentalOrderItemRequest): RentalOrderResponse {
        val rentalOrder = rentalOrderService.getRentalById(rentalOrderId)
        val equipmentItem = equipmentService.getEquipmentByBarcode(rentalOrderItemRequest.barCode)
        validateEquipment(equipmentItem, rentalOrder.id!!)
        equipmentItem.status = EquipmentStatus.IN_USE
        val rentalOrderItem = RentalOrderItem(
            id = null,
            rentalOrder,
            equipmentItem
        )

        rentalOrderItemRepository.save(rentalOrderItem)
        val newRentalOrder = rentalOrderService.getRentalById(rentalOrderId)
        return rentalOrderMapper.toResponse(newRentalOrder)
    }

    private fun validateEquipment(equipment: Equipment, rentalOrderId: Long) {

        val rentalOrderItemsCount = rentalOrderItemRepository.findAllByRentalOrderId(rentalOrderId).count()
        if (rentalOrderItemsCount >= rentalItemProperties.countEquipmentPerRentalOrder) {
            throw TooMuchEquipmentPerOneRentalOrderException("Max count of equipment per one rental is ${rentalItemProperties.countEquipmentPerRentalOrder} is reached")
        }
        if (equipment.status == EquipmentStatus.IN_USE) {
             throw EquipmentAlreadyInUseException("Equipment with bar code ${equipment.barCode} already in use.")
        }
    }

    @Transactional
    override fun deleteRentalOrderItem(rentalOrderItemId: Long) {
        val rentalOrderItem = getOrThrow(rentalOrderItemId)
        rentalOrderItemRepository.delete(rentalOrderItem)
    }

    @Transactional(readOnly = true)
    override fun getRentalOrderItemsByRentalOrderId(rentalOrderId: Long): RentalOrderItemListResponse {
        rentalOrderService.getRentalById(rentalOrderId)
        val rentalOrderItems = rentalOrderItemRepository.findAllByRentalOrderId(rentalOrderId)

        val rentalOrderItemList = rentalOrderItems.map { roi ->
            RentalOrderItemResponse(
                roi.equipment.type.name,
                roi.equipment.barCode
            )
        }

        val rentalOrderListResponse = RentalOrderItemListResponse(
            rentalOrderId,
            rentalOrderItemList
        )
        return rentalOrderListResponse
    }

    private fun getOrThrow(rentalOrderItemId: Long): RentalOrderItem {
        val rentalOrderItem = rentalOrderItemRepository.findById(rentalOrderItemId)
        if (rentalOrderItem.isPresent) {
            return rentalOrderItem.get()
        } else {
            throw RentalOrderItemNotFoundException("Rental order item with id $rentalOrderItemId not found")
        }
    }
}
