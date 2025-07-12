package com.project.order_serivce.events;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderPlacedEvent {
    private Long orderId;
    private Long userId;
    private Integer total;
}
