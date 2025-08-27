package com.fooddelivery.deliveryservice.service

import com.fooddelivery.deliveryservice.domain.DeliveryEntity
import com.fooddelivery.deliveryservice.domain.DeliveryStatus
import com.fooddelivery.deliveryservice.dto.DeliveryResponse
import com.fooddelivery.deliveryservice.events.DeliveryEventProducer
import com.fooddelivery.deliveryservice.repository.DeliveryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class DeliveryService(
    private val repository: DeliveryRepository,
    private val eventProducer: DeliveryEventProducer
) {

    @Transactional(readOnly = true)
    fun listDeliveries(): List<DeliveryResponse> =
        repository.findAll().map { it.toResponse() }

    @Transactional
    fun assignDriver(id: Long, driverName: String): DeliveryResponse {
        val delivery = repository.findById(id).orElseThrow { NoSuchElementException("Delivery $id not found") }
        delivery.driverName = driverName
        delivery.status = DeliveryStatus.ASSIGNED
        delivery.updatedAt = LocalDateTime.now()
        return repository.save(delivery).toResponse()
    }

    @Transactional
    fun startDelivery(id: Long): DeliveryResponse {
        val delivery = repository.findById(id).orElseThrow { NoSuchElementException("Delivery $id not found") }
        if (delivery.driverName == null) throw IllegalStateException("Driver must be assigned before starting delivery")
        delivery.status = DeliveryStatus.IN_PROGRESS
        delivery.updatedAt = LocalDateTime.now()
        repository.save(delivery)
        eventProducer.sendDeliveryStarted(delivery.orderId, delivery.driverName!!)
        return delivery.toResponse()
    }

    @Transactional
    fun completeDelivery(id: Long): DeliveryResponse {
        val delivery = repository.findById(id).orElseThrow { NoSuchElementException("Delivery $id not found") }
        if (delivery.driverName == null) throw IllegalStateException("Driver must be assigned before completing delivery")
        delivery.status = DeliveryStatus.COMPLETED
        delivery.updatedAt = LocalDateTime.now()
        repository.save(delivery)
        eventProducer.sendDeliveryCompleted(delivery.orderId, delivery.driverName!!)
        return delivery.toResponse()
    }

    private fun DeliveryEntity.toResponse() = DeliveryResponse(
        id = this.id,
        orderId = this.orderId,
        driverName = this.driverName,
        status = this.status,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}