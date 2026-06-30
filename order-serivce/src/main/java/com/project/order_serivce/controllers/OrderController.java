package com.project.order_serivce.controllers;

import com.project.order_serivce.clients.UserClient;
import com.project.order_serivce.dtos.UserDTO;
import com.project.order_serivce.models.Order;
import com.project.order_serivce.services.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;
    private final UserClient userClient;

//    @PostMapping("")
//    public ResponseEntity<?> createOrder(@RequestBody Order order) {
//        return ResponseEntity.ok(orderService.createOrder(order));
//    }

//    For Jwt
//    @PostMapping("")
//    public ResponseEntity<?> createOrder(@RequestBody Order order) {
//        Long userId= (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        order.setUserId(userId);
//        return ResponseEntity.ok(orderService.createOrder(order));
//    }

//    For Keycloak
    @PostMapping("")
    public Order createOrder(@RequestBody Order order, JwtAuthenticationToken authenticationToken){
        String sub = authenticationToken.getToken().getSubject();
        UserDTO userDTO = userClient.getUserByKeycloakId(sub);

        order.setUserId(userDTO.getId());
        return orderService.createOrder(order);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(orderService.getOrderById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
