package com.project.notification_service.listeners;

import com.project.notification_service.events.OrderPlacedEvent;
import com.project.notification_service.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventListener {
    private final EmailService emailService;

    @KafkaListener(
            topics = "order-topic",
            groupId = "notification-group",
            containerFactory = "orderPlacedEventConcurrentKafkaListenerContainerFactory"
    )
    public void handleOrderEvent(OrderPlacedEvent orderPlacedEvent) {
        System.out.println("Received event from Kafka: " + orderPlacedEvent);

        // Thuc hien gui email o day
        emailService.sendOrderEmail(orderPlacedEvent);
    }
}
