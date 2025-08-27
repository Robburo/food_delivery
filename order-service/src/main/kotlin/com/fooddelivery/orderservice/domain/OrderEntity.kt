package com.fooddelivery.orderservice.domain

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
data class OrderEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val customerName: String,

    @Column(nullable = false)
    val restaurantId: Long,

    @Column(nullable = false, columnDefinition = "TEXT")
    val items: String,

    @Column(nullable = false, precision = 10, scale = 2)
    val totalPrice: BigDecimal,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.PLACED,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)
