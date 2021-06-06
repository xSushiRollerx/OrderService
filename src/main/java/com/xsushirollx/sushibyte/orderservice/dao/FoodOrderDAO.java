package com.xsushirollx.sushibyte.orderservice.dao;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xsushirollx.sushibyte.orderservice.model.FoodOrder;

@Repository
public interface FoodOrderDAO extends JpaRepository<FoodOrder, Long> {

	List<FoodOrder> findByCustomerId(Long customerId);

	boolean existsByIdAndState(long id, int state);

}
