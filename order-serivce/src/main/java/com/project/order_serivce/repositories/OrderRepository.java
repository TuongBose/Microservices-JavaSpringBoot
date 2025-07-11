package com.project.order_serivce.repositories;

import com.project.order_serivce.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
