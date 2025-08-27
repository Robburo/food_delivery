package com.fooddelivery.notification_service.events

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class NotificationEventConsumer {

    private val log = LoggerFactory.getLogger(NotificationEventConsumer::class.java)

    @KafkaListener(topics = ["order_placed"], groupId = "notification-service-group")
    fun handleOrderPlaced(record: ConsumerRecord<String, String>) {
        log.info("Notification: New order placed -> {}", record.value())
    }

    @KafkaListener(topics = ["payment_success"], groupId = "notification-service-group")
    fun handlePaymentSuccess(record: ConsumerRecord<String, String>) {
        log.info("Notification: Payment successful -> {}", record.value())
    }

    @KafkaListener(topics = ["payment_failed"], groupId = "notification-service-group")
    fun handlePaymentFailed(record: ConsumerRecord<String, String>) {
        log.info("Notification: Payment failed -> {}", record.value())
    }

    @KafkaListener(topics = ["food_ready"], groupId = "notification-service-group")
    fun handleFoodReady(record: ConsumerRecord<String, String>) {
        log.info("Notification: Food is ready -> {}", record.value())
    }

    @KafkaListener(topics = ["delivery_started"], groupId = "notification-service-group")
    fun handleDeliveryStarted(record: ConsumerRecord<String, String>) {
        log.info("Notification: Delivery started -> {}", record.value())
    }

    @KafkaListener(topics = ["delivery_completed"], groupId = "notification-service-group")
    fun handleDeliveryCompleted(record: ConsumerRecord<String, String>) {
        log.info("Notification: Delivery completed -> {}", record.value())
    }
}