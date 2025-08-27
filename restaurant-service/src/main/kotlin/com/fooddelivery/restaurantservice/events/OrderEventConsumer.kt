package com.fooddelivery.restaurantservice.events

import com.fasterxml.jackson.databind.ObjectMapper
import com.fooddelivery.restaurantservice.domain.RestaurantOrderEntity
import com.fooddelivery.restaurantservice.repository.RestaurantOrderRepository
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class OrderEventConsumer(
    private val repository: RestaurantOrderRepository,
    private val objectMapper: ObjectMapper
) {
    private val log = LoggerFactory.getLogger(OrderEventConsumer::class.java)

    @KafkaListener(topics = ["order_placed"], groupId = "restaurant-service-group")
    @Transactional
    fun handleOrderPlaced(record: ConsumerRecord<String, String>) {
        try {
            val event = objectMapper.readValue(record.value(), OrderPlacedEvent::class.java)
            log.info("Received order_placed event: {}", event)

            val entity = RestaurantOrderEntity(
                orderId = event.orderId,
                customerName = event.customerName,
                items = event.items,
                totalPrice = event.totalPrice
            )
            repository.save(entity)
            log.info("Saved order ${event.orderId} for restaurant")
        } catch (ex: Exception) {
            log.error("Failed to process order_placed event: ${record.value()}", ex)
        }
    }
}