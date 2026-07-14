package com.project.order_serivce.services;

import com.project.order_serivce.clients.UserClient;
import com.project.order_serivce.dtos.UserDTO;
import com.project.order_serivce.events.OrderCancelledEvent;
import com.project.order_serivce.events.OrderCompletedEvent;
import com.project.order_serivce.events.OrderCreatedEvent;
import com.project.order_serivce.events.OrderPlacedEvent;
import com.project.order_serivce.models.Order;
import com.project.order_serivce.models.OrderStatus;
import com.project.order_serivce.producers.OrderEventProducer;
import com.project.order_serivce.repositories.OrderRepository;
import com.project.order_serivce.responses.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private static final String DEFAULT_CANCEL_REASON = "Order cancelled (unspecified reason)";

    private final OrderRepository orderRepository;
    private final UserClient userClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
    private final OrderEventProducer orderEventProducer;

//    @Override
//    public Order createOrder(Order order) {
//        return orderRepository.save(order);
//    }

    // Use kafka
//    @Override
//    public Order createOrder(Order order) {
//        Order newOrder = orderRepository.save(order);
//
//        // Send event Kafka
//        OrderPlacedEvent orderPlacedEvent = OrderPlacedEvent
//                .builder()
//                .orderId(newOrder.getId())
//                .userId(newOrder.getUserId())
//                .total(newOrder.getPrice())
//                .build();
//
//        kafkaTemplate.send("order-topic", orderPlacedEvent);
//        System.out.println("Send Kafka event:"+orderPlacedEvent);
//
//        return newOrder;
//    }

    @Override
    public Order createOrder(Order order) {
        order.setStatus(OrderStatus.PENDING);
        Order saved = orderRepository.save(order);

        // Create event
        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .orderId(saved.getId())
                .userId(saved.getUserId())
                .productId(saved.getProductId())
                .quantity(saved.getQuantity())
                .total(saved.getTotal())
                .build();

        // Publish event to producer
        orderEventProducer.publishOrderCreatedEvent(event);

        return saved;
    }

    @Override
    public OrderResponse getOrderById(Long id) throws Exception {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderId does not exist"));
        UserDTO userDTO = userClient.getUserById(existingOrder.getUserId());

        return OrderResponse.fromOrderAndUserDTO(existingOrder, userDTO);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

//    @Override
//    public void updateOrderStatus(Long orderId, OrderStatus status) {
//        orderRepository.findById(orderId).ifPresent(order -> {
//            order.setStatus(status);
//            Order updated = orderRepository.save(order);
//            System.out.println("OrderStatus updated: " + status);
//
//            // If the order status is COMPLETED, publish an OrderCompletedEvent
//            if (status == OrderStatus.COMPLETED) {
//                OrderCompletedEvent event = new OrderCompletedEvent(
//                        updated.getId(),
//                        updated.getUserId(),
//                        status.name()
//                );
//                orderEventProducer.publishOrderCompletedEvent(event);
//            }
//
//            // If Cancel then publish event cancelled and release stock
//            if (status == OrderStatus.CANCELLED) {
//                OrderCancelledEvent event = new OrderCancelledEvent(
//                        updated.getId(),
//                        updated.getUserId(),
//                        updated.getProductId(),
//                        updated.getQuantity(),
//                        "Order cancelled (payment failed)"
//                );
//                orderEventProducer.publishOrderCancelledEvent(event);
//            }
//        });
//    }

    @Override
    public void updateOrderStatus(Long orderId, OrderStatus status) {
        updateOrderStatus(orderId, status, DEFAULT_CANCEL_REASON);
        // default reason if not provided
    }

    @Override
    public void updateOrderStatus(Long orderId, OrderStatus status, String reason) {
        orderRepository.findById(orderId).ifPresent(order -> {
            order.setStatus(status);
            Order updated = orderRepository.save(order);
            System.out.println("Order status updated to: " + status);

            if (status == OrderStatus.COMPLETED) {
                OrderCompletedEvent event = new OrderCompletedEvent(
                        updated.getId(),
                        updated.getUserId(),
                        status.name()
                );
                orderEventProducer.publishOrderCompletedEvent(event);
            }

            if (status == OrderStatus.CANCELLED) {
                OrderCancelledEvent event = new OrderCancelledEvent(
                        updated.getId(),
                        updated.getUserId(),
                        updated.getProductId(),
                        updated.getQuantity(),
                        reason != null ? reason : DEFAULT_CANCEL_REASON
                );
                orderEventProducer.publishOrderCancelledEvent(event);
            }
        });
    }
}
