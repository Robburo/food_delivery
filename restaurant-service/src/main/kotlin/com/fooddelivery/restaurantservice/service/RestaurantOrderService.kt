package com.fooddelivery.restaurantservice.service

import com.fooddelivery.restaurantservice.domain.RestaurantOrderEntity
import com.fooddelivery.restaurantservice.domain.RestaurantOrderStatus
import com.fooddelivery.restaurantservice.dto.RestaurantOrderResponse
import com.fooddelivery.restaurantservice.events.RestaurantEventProducer
import com.fooddelivery.restaurantservice.repository.RestaurantOrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RestaurantOrderService(
    private val repository: RestaurantOrderRepository,
    private val eventProducer: RestaurantEventProducer
) {

    @Transactional(readOnly = true)
    fun listOrders(): List<RestaurantOrderResponse> =
        repository.findAll().map { it.toResponse() }

    @Transactional
    fun markPreparing(id: Long): RestaurantOrderResponse {
        val order = repository.findById(id).orElseThrow { NoSuchElementException("Order $id not found") }
        order.status = RestaurantOrderStatus.PREPARING
        return repository.save(order).toResponse()
    }

    @Transactional
    fun markReady(id: Long): RestaurantOrderResponse {
        val order = repository.findById(id).orElseThrow { NoSuchElementException("Order $id not found") }
        order.status = RestaurantOrderStatus.READY
        repository.save(order)
        eventProducer.sendFoodReady(order.orderId)
        return order.toResponse()
    }

    @Transactional
    fun cancelOrder(id: Long): RestaurantOrderResponse {
        val order = repository.findById(id).orElseThrow { NoSuchElementException("Order $id not found") }
        order.status = RestaurantOrderStatus.CANCELLED
        repository.save(order)
        eventProducer.sendOrderCancelled(order.orderId)
        return order.toResponse()
    }

    private fun RestaurantOrderEntity.toResponse() = RestaurantOrderResponse(
        id = this.id,
        orderId = this.orderId,
        customerName = this.customerName,
        items = this.items,
        totalPrice = this.totalPrice,
        status = this.status,
        createdAt = this.createdAt
    )
}
