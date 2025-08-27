package com.fooddelivery.deliveryservice.repository

import com.fooddelivery.deliveryservice.domain.DeliveryEntity
import com.fooddelivery.deliveryservice.domain.DeliveryStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DeliveryRepository : JpaRepository<DeliveryEntity, Long> {
    fun findByStatus(status: DeliveryStatus): List<DeliveryEntity>
}