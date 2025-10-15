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
        val future = kafkaTemplate.send(topic, event.orderId.toString(), message)

        future.whenComplete { result, ex ->
            if (ex == null) {
                val meta = result.recordMetadata
                log.info(
                    "Successfully sent order event. topic={}, partition={}, offset={}, key={}, message={}",
                    meta.topic(),
                    meta.partition(),
                    meta.offset(),
                    event.orderId,
                    message
                )
            } else {
                log.error(
                    "Failed to send order event. topic={}, key={}, message={}",
                    topic,
                    event.orderId,
                    message,
                    ex
                )
            }
        }
    }
}

