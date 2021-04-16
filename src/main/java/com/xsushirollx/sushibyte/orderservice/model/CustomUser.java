//package com.xsushirollx.sushibyte.orderservice.model;
//
//import java.util.List;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//
////@Entity
////@Table(name = "user")
//public class CustomUser {
//
//	@Column(name = "id")
//	Integer userId;
//	
//	@Column(name = "role")
//	Integer role;
//	
//	@OneToMany
//	@JoinColumn(name = "customer_id")
//	List<FoodOrder> order;
//	public Integer getUserId() {
//		return userId;
//	}
//	public void setUserId(Integer userId) {
//		this.userId = userId;
//	}
//	public Integer getRole() {
//		return role;
//	}
//	public void setRole(Integer role) {
//		this.role = role;
//	}
//	public List<FoodOrder> getOrder() {
//		return order;
//	}
//	public void setOrder(List<FoodOrder> order) {
//		this.order = order;
//	}
//	
//	
//	
//}
