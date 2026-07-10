package com.project.order_serivce.events.consumers;

import lombok.Data;

@Data
public class PaymentCompletedEvent {
    private Long orderId;
    private String paymentId;
    private double amount;
}
