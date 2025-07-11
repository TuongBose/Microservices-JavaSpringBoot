package com.project.order_serivce.services;

import com.project.order_serivce.models.Order;

import java.util.List;

public interface IOrderService {
    Order createOrder(Order order);
    List<Order> getAllOrders();
}
