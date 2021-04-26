package com.xsushirollx.sushibyte.orderservice.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xsushirollx.sushibyte.orderservice.model.Customer;
import com.xsushirollx.sushibyte.orderservice.model.FoodOrder;

@Repository
public interface CustomerDAO extends JpaRepository<Customer, Integer> {

	boolean existsByUsernameAndRole(String customer, int i);

	boolean existsByEmailAndRole(String customer, int i);

	Customer findByUsername(String customer);

	Customer findByEmail(String customer);

	Page<FoodOrder> findById(String string, Pageable of);

	Page<FoodOrder> findByEmail(String string, Pageable of);

	Page<FoodOrder> findByUsername(String string, Pageable of);

}
