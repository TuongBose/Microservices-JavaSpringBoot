package com.project.order_serivce.producers;

import com.project.order_serivce.events.OrderCancelledEvent;
import com.project.order_serivce.events.OrderCompletedEvent;
import com.project.order_serivce.events.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC_ORDER_CREATED = "orders";
    private static final String TOPIC_ORDER_COMPLETED = "orders_completed";
    private static final String TOPIC_ORDER_CANCELLED = "orders_cancelled";

    public void publishOrderCreatedEvent(OrderCreatedEvent event) {
        System.out.println("Sent OrderCreatedEvent: " + event);
        kafkaTemplate.send(TOPIC_ORDER_CREATED, String.valueOf(event.getOrderId()), event);
    }

    public void publishOrderCompletedEvent(OrderCompletedEvent event) {
        System.out.println("Sent OrderCompletedEvent: " + event);
        kafkaTemplate.send(TOPIC_ORDER_COMPLETED, String.valueOf(event.getOrderId()), event);
    }

    public void publishOrderCancelledEvent(OrderCancelledEvent event) {
        System.out.println("Sent OrderCancelledEvent: " + event);
        kafkaTemplate.send(TOPIC_ORDER_CANCELLED, String.valueOf(event.getOrderId()), event);
    }
}
