package com.fooddelivery.deliveryservice.controller

import com.fooddelivery.deliveryservice.dto.DeliveryResponse
import com.fooddelivery.deliveryservice.service.DeliveryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/deliveries")
class DeliveryController(
    private val service: DeliveryService
) {

    @GetMapping
    fun listDeliveries(): ResponseEntity<List<DeliveryResponse>> =
        ResponseEntity.ok(service.listDeliveries())

    @PutMapping("/{id}/assign")
    fun assignDriver(
        @PathVariable id: Long,
        @RequestParam driverName: String
    ): ResponseEntity<DeliveryResponse> =
        ResponseEntity.ok(service.assignDriver(id, driverName))

    @PutMapping("/{id}/start")
    fun startDelivery(@PathVariable id: Long): ResponseEntity<DeliveryResponse> =
        ResponseEntity.ok(service.startDelivery(id))

    @PutMapping("/{id}/complete")
    fun completeDelivery(@PathVariable id: Long): ResponseEntity<DeliveryResponse> =
        ResponseEntity.ok(service.completeDelivery(id))
}