package com.project.order_serivce.services;

import com.project.order_serivce.models.Order;
import com.project.order_serivce.responses.OrderResponse;

import java.util.List;

public interface IOrderService {
    Order createOrder(Order order);
    OrderResponse getOrderById(Long id) throws Exception;
    List<Order> getAllOrders();
}
