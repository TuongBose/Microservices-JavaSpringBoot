package com.project.order_serivce.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id")
    private Long userId;

    private String product;
    private Double price;

    @Column(name="product_id")
    private Long productId;

    private int quantity;
    private Double total;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
