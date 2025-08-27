package com.fooddelivery.deliveryservice.events

import com.fasterxml.jackson.databind.ObjectMapper
import com.fooddelivery.deliveryservice.domain.DeliveryEntity
import com.fooddelivery.deliveryservice.domain.DeliveryStatus
import com.fooddelivery.deliveryservice.repository.DeliveryRepository
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Component
class DeliveryEventConsumer(
    private val repository: DeliveryRepository,
    private val objectMapper: ObjectMapper
) {
    private val log = LoggerFactory.getLogger(DeliveryEventConsumer::class.java)

    @KafkaListener(topics = ["food_ready"], groupId = "delivery-service-group")
    @Transactional
    fun handleFoodReady(record: ConsumerRecord<String, String>) {
        try {
            val event = objectMapper.readValue(record.value(), FoodReadyEvent::class.java)
            log.info("Received food_ready event: {}", event)

            val delivery = DeliveryEntity(
                orderId = event.orderId,
                status = DeliveryStatus.PENDING,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
            repository.save(delivery)
            log.info("Created delivery task for orderId=${event.orderId}")
        } catch (ex: Exception) {
            log.error("Failed to process food_ready event: ${record.value()}", ex)
        }
    }
}