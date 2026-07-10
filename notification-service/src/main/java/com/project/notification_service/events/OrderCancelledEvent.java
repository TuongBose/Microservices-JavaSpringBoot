package com.project.notification_service.events;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCancelledEvent {
    private Long orderId;
    private Long userId;
    private Long productId;
    private int quantity;
    private String reason;
}
