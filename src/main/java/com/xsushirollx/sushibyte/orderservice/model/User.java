package com.xsushirollx.sushibyte.orderservice.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User  {

	@Id
	@Column(name = "id")
	Long id;
	
	@Column(name = "user_role")
	Integer role;
	
	@Column(name = "username")
	String username;
	
	@Column(name = "email")
	String email;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id")
	List<FoodOrder> orders;
	
	public User() {
		super();
	}

	public User(Long id, Integer role) {
		super();
		this.id = id;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public List<FoodOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<FoodOrder> order) {
		this.orders = order;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", role=" + role + ", orders=" + orders + "]";
	}
	
	

}
