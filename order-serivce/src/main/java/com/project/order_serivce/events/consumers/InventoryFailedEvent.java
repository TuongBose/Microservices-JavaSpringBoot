package com.project.order_serivce.events.consumers;

import lombok.Data;

@Data
public class InventoryFailedEvent {
    private Long orderId;
    private String status;
    private String message;
}
