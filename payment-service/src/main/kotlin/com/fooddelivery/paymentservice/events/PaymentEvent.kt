package com.fooddelivery.paymentservice.events

import java.math.BigDecimal

data class PaymentEvent(
    val orderId: Long,
    val amount: BigDecimal,
    val status: String
)