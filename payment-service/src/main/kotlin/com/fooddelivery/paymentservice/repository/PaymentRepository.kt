package com.fooddelivery.paymentservice.repository

import com.fooddelivery.paymentservice.domain.PaymentEntity
import com.fooddelivery.paymentservice.domain.PaymentStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentRepository : JpaRepository<PaymentEntity, Long> {
    fun findByStatus(status: PaymentStatus): List<PaymentEntity>
}