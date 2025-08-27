package com.fooddelivery.deliveryservice.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "deliveries")
data class DeliveryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val orderId: Long,

    @Column(nullable = true)
    var driverName: String? = null,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: DeliveryStatus = DeliveryStatus.PENDING,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
)