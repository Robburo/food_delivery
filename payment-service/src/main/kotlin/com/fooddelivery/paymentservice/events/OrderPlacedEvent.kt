package com.fooddelivery.paymentservice.events

import java.math.BigDecimal

data class OrderPlacedEvent(
    val orderId: Long,
    val customerName: String,
    val restaurantId: Long,
    val items: String,
    val totalPrice: BigDecimal
)