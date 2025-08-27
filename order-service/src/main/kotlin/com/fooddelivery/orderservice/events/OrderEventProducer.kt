package com.fooddelivery.orderservice.events

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class OrderEventProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper
) {
    private val log = LoggerFactory.getLogger(OrderEventProducer::class.java)
    private val topic = "order_placed"

    fun sendOrderPlaced(event: OrderPlacedEvent) {
        val message = objectMapper.writeValueAsString(event)
        kafkaTemplate.send(topic, event.orderId.toString(), message)
        log.info("Sent order placed event: {}", message)
    }
}
