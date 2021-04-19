package com.xsushirollx.sushibyte.orderservice.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "food_order")
public class FoodOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "order_state")
	private Integer state;
	
	@Column(name = "customer_id")
	private Integer customerId;
	
	@Column(name = "is_refunded")
	private Integer refunded;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "order_id")
	private List<OrderItem> orderItems;
	
	@JsonIgnore
	@Column(name = "stripe")
	private Integer stripe;

	public FoodOrder() {
	}
	
	public FoodOrder(Integer id, Integer state) {
		this.id = id;
		this.state = state;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getRefunded() {
		return refunded;
	}

	public void setRefunded(Integer refunded) {
		this.refunded = refunded;
	}
	
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Integer getStripe() {
		return stripe;
	}

	public void setStripe(Integer stripe) {
		this.stripe = stripe;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FoodOrder other = (FoodOrder) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	

	
	
	
}
