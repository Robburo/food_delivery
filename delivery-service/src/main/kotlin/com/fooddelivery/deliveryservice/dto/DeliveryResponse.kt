package com.fooddelivery.deliveryservice.dto

import com.fooddelivery.deliveryservice.domain.DeliveryStatus
import java.time.LocalDateTime

data class DeliveryResponse(
    val id: Long,
    val orderId: Long,
    val driverName: String?,
    val status: DeliveryStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
