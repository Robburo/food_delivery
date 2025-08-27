package com.fooddelivery.paymentservice.events

import com.fasterxml.jackson.databind.ObjectMapper
import com.fooddelivery.paymentservice.domain.PaymentEntity
import com.fooddelivery.paymentservice.domain.PaymentStatus
import com.fooddelivery.paymentservice.repository.PaymentRepository
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import kotlin.random.Random

@Component
class OrderEventConsumer(
    private val repository: PaymentRepository,
    private val objectMapper: ObjectMapper,
    private val eventProducer: PaymentEventProducer
) {
    private val log = LoggerFactory.getLogger(OrderEventConsumer::class.java)

    @KafkaListener(topics = ["order_placed"], groupId = "payment-service-group")
    @Transactional
    fun handleOrderPlaced(record: ConsumerRecord<String, String>) {
        try {
            val event = objectMapper.readValue(record.value(), OrderPlacedEvent::class.java)
            log.info("Received order_placed event: {}", event)

            // Create a pending payment record
            val payment = PaymentEntity(
                orderId = event.orderId,
                amount = event.totalPrice,
                status = PaymentStatus.PENDING,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
            val saved = repository.save(payment)
            log.info("Created pending payment record for orderId=${event.orderId}")

            // Simulate processing (50/50 success/fail for demo)
            val success = Random.nextBoolean()
            saved.status = if (success) PaymentStatus.SUCCESS else PaymentStatus.FAILED
            saved.updatedAt = LocalDateTime.now()
            repository.save(saved)

            // Publish event
            val paymentEvent = PaymentEvent(
                orderId = saved.orderId,
                amount = saved.amount,
                status = saved.status.name
            )
            eventProducer.sendPaymentEvent(paymentEvent)

        } catch (ex: Exception) {
            log.error("Failed to process order_placed event: ${record.value()}", ex)
        }
    }
}