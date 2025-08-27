package com.fooddelivery.orderservice.dto

import java.math.BigDecimal

data class OrderRequest(
    val customerName: String,
    val restaurantId: Long,
    val items: String,
    val totalPrice: BigDecimal
)
