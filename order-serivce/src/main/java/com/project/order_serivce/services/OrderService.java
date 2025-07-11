package com.project.order_serivce.services;

import com.project.order_serivce.clients.UserClient;
import com.project.order_serivce.dtos.UserDTO;
import com.project.order_serivce.models.Order;
import com.project.order_serivce.repositories.OrderRepository;
import com.project.order_serivce.responses.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final UserClient userClient;

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public OrderResponse getOrderById(Long id) throws Exception {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(()->new RuntimeException("OrderId does not exist"));
        UserDTO userDTO = userClient.getUserById(existingOrder.getUserid());

        return OrderResponse.fromOrderAndUserDTO(existingOrder,userDTO);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
