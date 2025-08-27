package com.fooddelivery.restaurantservice.dto

import com.fooddelivery.restaurantservice.domain.RestaurantOrderStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class RestaurantOrderResponse(
    val id: Long,
    val orderId: Long,
    val customerName: String,
    val items: String,
    val totalPrice: BigDecimal,
    val status: RestaurantOrderStatus,
    val createdAt: LocalDateTime
)