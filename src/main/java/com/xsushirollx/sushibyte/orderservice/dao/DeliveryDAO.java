package com.xsushirollx.sushibyte.orderservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xsushirollx.sushibyte.orderservice.model.Delivery;

@Repository
public interface DeliveryDAO extends JpaRepository<Delivery, Integer> {

}
