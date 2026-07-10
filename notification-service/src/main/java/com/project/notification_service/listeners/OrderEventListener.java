package com.project.notification_service.listeners;

import com.project.notification_service.events.OrderCancelledEvent;
import com.project.notification_service.events.OrderCompletedEvent;
import com.project.notification_service.events.OrderPlacedEvent;
import com.project.notification_service.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventListener {
//    private final EmailService emailService;
//
//    @KafkaListener(
//            topics = "order-topic",
//            groupId = "notification-group",
//            containerFactory = "orderPlacedEventConcurrentKafkaListenerContainerFactory"
//    )
//    public void handleOrderEvent(OrderPlacedEvent orderPlacedEvent) {
//        System.out.println("Received event from Kafka: " + orderPlacedEvent);
//
//        // Thuc hien gui email o day
//        emailService.sendOrderEmail(orderPlacedEvent);
//    }

    @KafkaListener(
            topics = "orders_completed",
            groupId = "notification-service",
            containerFactory = "orderCompletedEventListenerFactory"
    )
    public void handleOrderCompleted(OrderCompletedEvent event) {
        System.out.println("Sent notification to user "
                + event.getUserId()
                + " about order "
                + event.getOrderId()
                + " with status: "
                + event.getStatus());
    }


    @KafkaListener(
            topics = "orders_cancelled",
            groupId = "notification-cancel-group",
            containerFactory = "orderCancelledEventListenerFactory"
    )
    public void handleOrderCancelled(OrderCancelledEvent event) {
        System.out.println("[Notification] User "
                + event.getUserId()
                + " order "
                + event.getOrderId()
                + " was canceled. Reason: "
                + event.getReason());
    }
}
