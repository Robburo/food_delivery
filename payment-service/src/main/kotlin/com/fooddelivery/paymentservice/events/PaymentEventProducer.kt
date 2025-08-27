package com.fooddelivery.paymentservice.events

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class PaymentEventProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper
) {
    private val log = LoggerFactory.getLogger(PaymentEventProducer::class.java)

    fun sendPaymentEvent(event: PaymentEvent) {
        val topic = if (event.status == "SUCCESS") "payment_success" else "payment_failed"
        val message = objectMapper.writeValueAsString(event)
        kafkaTemplate.send(topic, event.orderId.toString(), message)
        log.info("Sent {} event: {}", event.status, message)
    }
}