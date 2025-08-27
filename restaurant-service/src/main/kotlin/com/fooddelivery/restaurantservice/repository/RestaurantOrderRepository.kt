package com.fooddelivery.restaurantservice.repository

import com.fooddelivery.restaurantservice.domain.RestaurantOrderEntity
import com.fooddelivery.restaurantservice.domain.RestaurantOrderStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RestaurantOrderRepository : JpaRepository<RestaurantOrderEntity, Long> {
    fun findByStatus(status: RestaurantOrderStatus): List<RestaurantOrderEntity>
}
