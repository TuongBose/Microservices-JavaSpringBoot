package com.project.order_serivce.controllers;

import com.project.order_serivce.models.Order;
import com.project.order_serivce.services.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;

    @PostMapping("")
    public ResponseEntity<?> createOrder(@RequestBody Order order)
    {
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @GetMapping("")
    public ResponseEntity<?> getAllOrders()
    {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}
