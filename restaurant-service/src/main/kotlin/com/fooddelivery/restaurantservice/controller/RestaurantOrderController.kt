package com.fooddelivery.restaurantservice.controller

import com.fooddelivery.restaurantservice.dto.RestaurantOrderResponse
import com.fooddelivery.restaurantservice.service.RestaurantOrderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/restaurant/orders")
class RestaurantOrderController(
    private val service: RestaurantOrderService
) {

    @GetMapping
    fun listOrders(): ResponseEntity<List<RestaurantOrderResponse>> =
        ResponseEntity.ok(service.listOrders())

    @PutMapping("/{id}/preparing")
    fun markPreparing(@PathVariable id: Long): ResponseEntity<RestaurantOrderResponse> =
        ResponseEntity.ok(service.markPreparing(id))

    @PutMapping("/{id}/ready")
    fun markReady(@PathVariable id: Long): ResponseEntity<RestaurantOrderResponse> =
        ResponseEntity.ok(service.markReady(id))

    @PutMapping("/{id}/cancel")
    fun cancelOrder(@PathVariable id: Long): ResponseEntity<RestaurantOrderResponse> =
        ResponseEntity.ok(service.cancelOrder(id))
}