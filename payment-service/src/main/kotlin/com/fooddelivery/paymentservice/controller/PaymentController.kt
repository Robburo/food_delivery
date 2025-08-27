package com.fooddelivery.paymentservice.controller

import com.fooddelivery.paymentservice.domain.PaymentStatus
import com.fooddelivery.paymentservice.dto.PaymentResponse
import com.fooddelivery.paymentservice.service.PaymentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/payments")
class PaymentController(
    private val service: PaymentService
) {

    @GetMapping
    fun listAllPayments(): ResponseEntity<List<PaymentResponse>> =
        ResponseEntity.ok(service.listAllPayments())

    @GetMapping("/{id}")
    fun getPaymentById(@PathVariable id: Long): ResponseEntity<PaymentResponse> =
        ResponseEntity.ok(service.getPaymentById(id))

    @GetMapping("/status/{status}")
    fun listPaymentsByStatus(@PathVariable status: String): ResponseEntity<List<PaymentResponse>> {
        val enumStatus = PaymentStatus.valueOf(status.uppercase())
        return ResponseEntity.ok(service.listPaymentsByStatus(enumStatus))
    }
}