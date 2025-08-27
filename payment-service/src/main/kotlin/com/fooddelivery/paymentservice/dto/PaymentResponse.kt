package com.fooddelivery.paymentservice.dto

import com.fooddelivery.paymentservice.domain.PaymentStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class PaymentResponse(
    val id: Long,
    val orderId: Long,
    val amount: BigDecimal,
    val status: PaymentStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)