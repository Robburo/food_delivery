package com.fooddelivery.orderservice.controller

import com.fooddelivery.orderservice.dto.OrderRequest
import com.fooddelivery.orderservice.dto.OrderResponse
import com.fooddelivery.orderservice.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/orders")
class OrderController(
    private val orderService: OrderService
) {

    @PostMapping
    fun createOrder(@RequestBody request: OrderRequest): ResponseEntity<OrderResponse> {
        val created = orderService.createOrder(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(created)
    }

    @GetMapping("/{id}")
    fun getOrderById(@PathVariable id: Long): ResponseEntity<OrderResponse> =
        ResponseEntity.ok(orderService.getOrderById(id))

    @GetMapping
    fun listOrders(): ResponseEntity<List<OrderResponse>> =
        ResponseEntity.ok(orderService.listOrders())
}
