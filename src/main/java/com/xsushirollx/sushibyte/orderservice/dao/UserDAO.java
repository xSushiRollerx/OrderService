package com.xsushirollx.sushibyte.orderservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xsushirollx.sushibyte.orderservice.model.User;

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {

}
