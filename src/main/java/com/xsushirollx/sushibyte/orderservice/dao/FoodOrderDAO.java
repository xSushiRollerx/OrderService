package com.xsushirollx.sushibyte.orderservice.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xsushirollx.sushibyte.orderservice.model.FoodOrder;

@Repository
public interface FoodOrderDAO extends JpaRepository<FoodOrder, Long> {

	Page<FoodOrder> findByCustomerId(Long customerId, PageRequest pageRequest);

	boolean existsByIdAndState(long id, int state);

}
