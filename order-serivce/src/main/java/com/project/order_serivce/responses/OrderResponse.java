package com.project.order_serivce.responses;

import com.project.order_serivce.dtos.UserDTO;
import com.project.order_serivce.models.Order;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Long id;
    private UserDTO userDTO;
    private String product;
    private Integer price;

    public static OrderResponse fromOrderAndUserDTO (Order order, UserDTO userDTO){
        return OrderResponse
                .builder()
                .id(order.getId())
                .userDTO(userDTO)
                .product(order.getProduct())
                .price(order.getPrice())
                .build();
    }
}
