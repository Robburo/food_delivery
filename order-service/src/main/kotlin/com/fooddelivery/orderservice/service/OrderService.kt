package com.fooddelivery.orderservice.service

import com.fooddelivery.orderservice.domain.OrderEntity
import com.fooddelivery.orderservice.domain.OrderStatus
import com.fooddelivery.orderservice.dto.OrderRequest
import com.fooddelivery.orderservice.dto.OrderResponse
import com.fooddelivery.orderservice.events.OrderEventProducer
import com.fooddelivery.orderservice.events.OrderPlacedEvent
import com.fooddelivery.orderservice.repository.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderEventProducer: OrderEventProducer
) {

    @Transactional
    fun createOrder(request: OrderRequest): OrderResponse {
        val order = OrderEntity(
            customerName = request.customerName,
            restaurantId = request.restaurantId,
            items = request.items,
            totalPrice = request.totalPrice,
            status = OrderStatus.PLACED
        )
        val saved = orderRepository.save(order)

        // Publish Kafka event
        orderEventProducer.sendOrderPlaced(
            OrderPlacedEvent(
                orderId = saved.id,
                customerName = saved.customerName,
                restaurantId = saved.restaurantId,
                items = saved.items,
                totalPrice = saved.totalPrice
            )
        )

        return saved.toResponse()
    }

    @Transactional(readOnly = true)
    fun getOrderById(id: Long): OrderResponse =
        orderRepository.findById(id)
            .orElseThrow { NoSuchElementException("Order not found with id=$id") }
            .toResponse()

    @Transactional(readOnly = true)
    fun listOrders(): List<OrderResponse> =
        orderRepository.findAll().map { it.toResponse() }

    private fun OrderEntity.toResponse() = OrderResponse(
        id = this.id,
        customerName = this.customerName,
        restaurantId = this.restaurantId,
        items = this.items,
        totalPrice = this.totalPrice,
        status = this.status,
        createdAt = this.createdAt
    )
}