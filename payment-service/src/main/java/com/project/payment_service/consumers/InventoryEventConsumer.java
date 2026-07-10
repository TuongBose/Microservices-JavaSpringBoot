package com.project.payment_service.consumers;

import com.project.payment_service.models.events.InventoryReservedEvent;
import com.project.payment_service.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryEventConsumer {
    private final PaymentService paymentService;

    @KafkaListener(topics = "inventory-reserved", groupId = "payment-service")
    public void handleInventoryReserved(InventoryReservedEvent event) {
        System.out.printf("PaymentService received InventoryReservedEvent: orderId=%d, status=%s, message=%s%n",
            event.getOrderId(), event.getStatus(), event.getMessage());

        paymentService.processPayment(event);
    }
}