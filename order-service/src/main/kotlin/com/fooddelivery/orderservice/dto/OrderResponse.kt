package com.fooddelivery.orderservice.dto

import com.fooddelivery.orderservice.domain.OrderStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderResponse(
    val id: Long,
    val customerName: String,
    val restaurantId: Long,
    val items: String,
    val totalPrice: BigDecimal,
    val status: OrderStatus,
    val createdAt: LocalDateTime
)
