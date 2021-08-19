package com.xsushirollx.sushibyte.orderservice.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xsushirollx.sushibyte.orderservice.model.DriverFoodOrder;

@Repository
public interface DriverFoodOrderDAO extends JpaRepository<DriverFoodOrder, Long> {

	Page<DriverFoodOrder> findByState(Integer state, Pageable pageRequest);

	DriverFoodOrder findByIdAndState(Long orderId, Integer state);

}

