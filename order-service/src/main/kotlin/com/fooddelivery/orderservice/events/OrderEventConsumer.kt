package com.fooddelivery.orderservice.events

import com.fooddelivery.orderservice.domain.OrderStatus
import com.fooddelivery.orderservice.repository.OrderRepository
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class OrderEventConsumer(
    private val orderRepository: OrderRepository
) {
    private val log = LoggerFactory.getLogger(OrderEventConsumer::class.java)

    @KafkaListener(topics = ["food_ready"], groupId = "order-service-group")
    @Transactional
    fun handleFoodReady(record: ConsumerRecord<String, String>) {
        val orderId = record.key().toLong()
        val message = record.value()
        log.info("Received food_ready event for orderId=$orderId: $message")

        val order = orderRepository.findById(orderId)
        if (order.isPresent) {
            val entity = order.get()
            entity.status = OrderStatus.READY
            orderRepository.save(entity)
            log.info("Updated order $orderId to READY")
        } else {
            log.warn("Order with id=$orderId not found")
        }
    }

    @KafkaListener(topics = ["order_cancelled"], groupId = "order-service-group")
    @Transactional
    fun handleOrderCancelled(record: ConsumerRecord<String, String>) {
        val orderId = record.key().toLong()
        log.info("Received order_cancelled event for orderId=$orderId")

        val order = orderRepository.findById(orderId)
        if (order.isPresent) {
            val entity = order.get()
            entity.status = OrderStatus.CANCELLED
            orderRepository.save(entity)
            log.info("Updated order $orderId to CANCELLED")
        }
    }
}
