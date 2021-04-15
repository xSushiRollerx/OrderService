package com.xsushirollx.sushibyte.orderservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xsushirollx.sushibyte.orderservice.model.MenuItem;

@Repository
public interface MenuItemDAO extends JpaRepository<MenuItem, Integer>  {
}
