package com.project.order_serivce.events.consumers;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentFailedEvent {
    private Long orderId;
    private String reason;
}
