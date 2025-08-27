package com.fooddelivery.paymentservice.service

import com.fooddelivery.paymentservice.domain.PaymentEntity
import com.fooddelivery.paymentservice.domain.PaymentStatus
import com.fooddelivery.paymentservice.dto.PaymentResponse
import com.fooddelivery.paymentservice.repository.PaymentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PaymentService(
    private val repository: PaymentRepository
) {

    @Transactional(readOnly = true)
    fun getPaymentById(id: Long): PaymentResponse =
        repository.findById(id)
            .orElseThrow { NoSuchElementException("Payment with id=$id not found") }
            .toResponse()

    @Transactional(readOnly = true)
    fun listAllPayments(): List<PaymentResponse> =
        repository.findAll().map { it.toResponse() }

    @Transactional(readOnly = true)
    fun listPaymentsByStatus(status: PaymentStatus): List<PaymentResponse> =
        repository.findByStatus(status).map { it.toResponse() }

    private fun PaymentEntity.toResponse() = PaymentResponse(
        id = this.id,
        orderId = this.orderId,
        amount = this.amount,
        status = this.status,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}