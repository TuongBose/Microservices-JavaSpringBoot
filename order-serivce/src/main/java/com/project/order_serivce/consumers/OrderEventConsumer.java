package com.project.order_serivce.consumers;

import com.project.order_serivce.events.consumers.InventoryFailedEvent;
import com.project.order_serivce.events.consumers.PaymentCompletedEvent;
import com.project.order_serivce.events.consumers.PaymentFailedEvent;
import com.project.order_serivce.models.OrderStatus;
import com.project.order_serivce.services.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final IOrderService orderService;

//    @KafkaListener(
//            topics = "inventory-failed",
//            containerFactory = "inventoryFailedKafkaListenerContainerFactory"
//    )
//    public void handleInventoryFailed(InventoryFailedEvent event) {
//        System.out.println("Received InventoryFailedEvent: " + event);
//        orderService.updateOrderStatus(event.getOrderId(), OrderStatus.CANCELLED);
//    }

    @KafkaListener(
            topics = "inventory-failed",
            containerFactory = "inventoryFailedKafkaListenerContainerFactory"
    )
    public void handleInventoryFailed(InventoryFailedEvent event) {
        System.out.println("Received InventoryFailedEvent: "
                + "OrderId=" + event.getOrderId()
                + ", Status=" + event.getStatus()
                + ", Reason=" + event.getMessage());

        orderService.updateOrderStatus(event.getOrderId(), OrderStatus.CANCELLED, event.getMessage());
        System.out.println("Order " + event.getOrderId() + " has been cancelled due to: " + event.getMessage());
    }

    @KafkaListener(
            topics = "payments",
            containerFactory = "paymentCompletedKafkaListenerContainerFactory"
    )
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        System.out.println("Received PaymentCompletedEvent: " + event);
        orderService.updateOrderStatus(event.getOrderId(), OrderStatus.COMPLETED);
    }

    @KafkaListener(
            topics = "payments_failed",
            containerFactory = "paymentFailedKafkaListenerContainerFactory"
    )
    public void handlePaymentFailed(PaymentFailedEvent event) {
        System.out.println("Received PaymentFailedEvent: " + event);
        orderService.updateOrderStatus(event.getOrderId(), OrderStatus.CANCELLED, event.getReason());

        System.out.println("Order " + event.getOrderId() + " has been cancelled due to: " + event.getReason());
    }
}