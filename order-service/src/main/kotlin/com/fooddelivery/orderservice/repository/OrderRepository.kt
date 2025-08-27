package com.fooddelivery.orderservice.repository

import com.fooddelivery.orderservice.domain.OrderEntity
import com.fooddelivery.orderservice.domain.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<OrderEntity, Long> {
    fun findByStatus(status: OrderStatus): List<OrderEntity>
}
