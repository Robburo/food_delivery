package com.fooddelivery.restaurantservice.domain

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "restaurant_orders")
data class RestaurantOrderEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val orderId: Long, // references original order-service ID

    @Column(nullable = false)
    val customerName: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    val items: String,

    @Column(nullable = false, precision = 10, scale = 2)
    val totalPrice: BigDecimal,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: RestaurantOrderStatus = RestaurantOrderStatus.PLACED,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)
