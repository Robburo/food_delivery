package com.fooddelivery.restaurantservice.events

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class RestaurantEventProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper
) {
    private val log = LoggerFactory.getLogger(RestaurantEventProducer::class.java)

    fun sendFoodReady(orderId: Long) {
        val topic = "food_ready"
        kafkaTemplate.send(topic, orderId.toString(), """{"orderId": $orderId}""")
        log.info("Sent food_ready event for orderId=$orderId")
    }

    fun sendOrderCancelled(orderId: Long) {
        val topic = "order_cancelled"
        kafkaTemplate.send(topic, orderId.toString(), """{"orderId": $orderId}""")
        log.info("Sent order_cancelled event for orderId=$orderId")
    }
}
