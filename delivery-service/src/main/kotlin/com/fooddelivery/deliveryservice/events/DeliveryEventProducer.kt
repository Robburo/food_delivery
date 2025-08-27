package com.fooddelivery.deliveryservice.events

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class DeliveryEventProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper
) {
    private val log = LoggerFactory.getLogger(DeliveryEventProducer::class.java)

    fun sendDeliveryStarted(orderId: Long, driverName: String) {
        val topic = "delivery_started"
        val event = mapOf("orderId" to orderId, "driverName" to driverName)
        kafkaTemplate.send(topic, orderId.toString(), objectMapper.writeValueAsString(event))
        log.info("Sent delivery_started event for orderId=$orderId, driver=$driverName")
    }

    fun sendDeliveryCompleted(orderId: Long, driverName: String) {
        val topic = "delivery_completed"
        val event = mapOf("orderId" to orderId, "driverName" to driverName)
        kafkaTemplate.send(topic, orderId.toString(), objectMapper.writeValueAsString(event))
        log.info("Sent delivery_completed event for orderId=$orderId, driver=$driverName")
    }
}