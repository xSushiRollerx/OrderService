package com.xsushirollx.sushibyte.orderservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xsushirollx.sushibyte.orderservice.model.OrderItem;

@Repository
public interface OrderItemDAO extends JpaRepository<OrderItem, Long> {
}
