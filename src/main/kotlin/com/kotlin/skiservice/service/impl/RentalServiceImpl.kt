package com.kotlin.skiservice.service.impl

import com.kotlin.skiservice.dto.rental.RentalOrderRequest
import com.kotlin.skiservice.dto.rental.RentalOrderResponse
import com.kotlin.skiservice.dto.rental.item.RentalOrderItemListResponse
import com.kotlin.skiservice.entities.Client
import com.kotlin.skiservice.entities.RentalOrder
import com.kotlin.skiservice.entities.enums.DocumentType
import com.kotlin.skiservice.entities.status.CellStatus
import com.kotlin.skiservice.entities.status.QueueTicketStatus
import com.kotlin.skiservice.entities.status.RentalOrderStatus
import com.kotlin.skiservice.exception.RentalOrderHaveEquipmentInUseException
import com.kotlin.skiservice.exception.RentalOrderNotFoundException
import com.kotlin.skiservice.mapper.RentalOrderMapper
import com.kotlin.skiservice.repository.RentalRepository
import com.kotlin.skiservice.repository.TicketRepository
import com.kotlin.skiservice.service.CellService
import com.kotlin.skiservice.service.ClientService
import com.kotlin.skiservice.service.RentalOrderItemService
import com.kotlin.skiservice.service.RentalService
import org.springframework.context.annotation.Lazy
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class RentalServiceImpl(
    private val rentalRepository: RentalRepository,
    private val clientService: ClientService,
    @Lazy private val rentalItemService: RentalOrderItemService,
    private val rentalOrderMapper: RentalOrderMapper,
    private val cellService: CellService,
    private val ticketRepository: TicketRepository
) : RentalService {

    @Transactional(readOnly = true)
    override fun getRentalListByPage(page: Int, size: Int): Page<RentalOrder> {
        val pageRequest = PageRequest.of(page, size)
        val result = rentalRepository.findAll(pageRequest)
        return result
    }

    @Transactional
    override fun createRental(rentalOrderRequest: RentalOrderRequest): RentalOrderResponse {
        val client = clientService.getClientByTicketNumber(rentalOrderRequest.ticketNumber)
        val cell = cellService.getCell(rentalOrderRequest.cellNumber)

        val rentalOrder = RentalOrder(
            createdAt = LocalDateTime.now(),
            client = client,
            status = RentalOrderStatus.CREATED,
            documentType = DocumentType.valueOf(rentalOrderRequest.documentType),
            documentNumber = rentalOrderRequest.documentNumber,
            cell = cell
        )
        cell.status = CellStatus.OCCUPIED
        val savedRental = rentalRepository.save(rentalOrder)
        return rentalOrderMapper.toResponse(savedRental)
    }

    @Transactional
    override fun deleteRental(rentalId: Long) {
        val rental = getOrThrow(rentalId)
        rentalRepository.delete(rental)
    }

    @Transactional(readOnly = true)
    override fun getRentalById(rentalId: Long): RentalOrder {
        return getOrThrow(rentalId)
    }

    @Transactional
    override fun endRental(id: Long): RentalOrderResponse {
        val rental = getOrThrow(id)
        val equipmentList = rentalItemService.getRentalOrderItemsByRentalOrderId(id)

        validateEndRentalByEquipment(equipmentList)

        rental.status = RentalOrderStatus.COMPLETED
        rental.endAt = LocalDateTime.now()

        if (rental.cell != null) {
            rental.cell!!.status = CellStatus.FREE
        }

        val savedRentalOrder = rentalRepository.save(rental)
        return rentalOrderMapper.toEndResponse(savedRentalOrder)
    }

    @Deprecated("Fill skiPass is start rental")
    @Transactional
    override fun startRental(rentalId: Long): RentalOrderResponse {
        val rental = getOrThrow(rentalId)

        rental.status = RentalOrderStatus.ACTIVE
        rental.startAt = LocalDateTime.now()

        return rentalOrderMapper.toResponse(rental)
    }

    override fun getRentalByClientId(clientId: Long): RentalOrder {
        val rental = rentalRepository.findByClientId(clientId)
        if (rental.isPresent) {
            return rental.get()
        } else {
            throw RentalOrderNotFoundException("Rental with client with id $clientId not found")
        }
    }

    override fun getRental(id: Long): RentalOrderResponse {
        return rentalOrderMapper.toResponse(getOrThrow(id))
    }

    @Transactional
    override fun endEdit(rentalId: Long): RentalOrderResponse {
        val rental = getOrThrow(rentalId)
        val client = rental.client

        val ticket = client.queueTicket
        ticket?.status = QueueTicketStatus.RENTAL_ORDER_COMPLETED

        return rentalOrderMapper.toResponse(rental)
    }

    private fun validateEndRentalByEquipment(equipmentList: RentalOrderItemListResponse) {
        if (equipmentList.rentalOrderItemList.isNotEmpty()) {
            val barCodes = equipmentList.rentalOrderItemList.map { it.equipmentBarCode }
            throw RentalOrderHaveEquipmentInUseException("Rental order have equipment with bar codes $barCodes in use. Please remove equipment from rental")
        }
    }

    private fun getOrThrow(id : Long) : RentalOrder {
        val rentalOrder = rentalRepository.findById(id)
        if (rentalOrder.isPresent) {
            return rentalOrder.get()
        } else {
            throw RentalOrderNotFoundException("Rental order with id $id not found.")
        }
    }
}
