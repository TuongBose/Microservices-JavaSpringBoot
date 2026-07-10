package com.project.payment_service.services;

import com.project.payment_service.models.events.InventoryReservedEvent;
import com.project.payment_service.models.events.PaymentCompletedEvent;
import com.project.payment_service.models.events.PaymentFailedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final KafkaProducerService kafkaProducerService;

    public void processPayment(InventoryReservedEvent event) {
        try {
            // Simulate payment processing logic
            boolean success = Math.random() > 0.2;

            if (success) {
                PaymentCompletedEvent completed = new PaymentCompletedEvent();
                completed.setOrderId(event.getOrderId());
                completed.setPaymentId(UUID.randomUUID().toString());
                completed.setAmount(100.0); // example fixed amount

                kafkaProducerService.sendPaymentCompleted(completed);
                System.out.println("Payment success -> sent PaymentCompletedEvent");
            } else {
                PaymentFailedEvent failed = new PaymentFailedEvent();
                failed.setOrderId(event.getOrderId());
                failed.setReason("Insufficient funds");

                kafkaProducerService.sendPaymentFailed(failed);
                System.out.println("Payment failed -> sent PaymentFailedEvent");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
