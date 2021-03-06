package com.xsushirollx.sushibyte.orderservice.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "food_order")
public class DriverFoodOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false)
	private Long id;

	@Column(name = "order_state", insertable = false)
	private Integer state;

	@Column(name = "customer_id", updatable = false)
	private Long customerId;

	@Column(name = "is_refunded", insertable = false)
	private Integer refunded;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = false)
	private List<OrderItem> orderItems;

	@OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = false)
	@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
	private Delivery address;
	
	@OneToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

	@JsonIgnore
	@Column(name = "stripe", updatable = false)
	private String stripe;

	@Column(name = "date_submitted", updatable = false, insertable = false)
	private String dateSubmitted;
	
	public DriverFoodOrder() {
	}

	public DriverFoodOrder(Long id, Integer state) {
		this.id = id;
		this.state = state;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
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

	public Delivery getAddress() {
		return address;
	}

	public void setAddress(Delivery address) {
		this.address = address;
	}

	public String getStripe() {
		return stripe;
	}

	public void setStripe(String stripe) {
		this.stripe = stripe;
	}

	public String getDateSubmitted() {
		return dateSubmitted;
	}

	@Override
	public String toString() {
		return "FoodOrder [id=" + id + ", state=" + state + ", customerId=" + customerId + ", refunded=" + refunded
				+ ", orderItems=" + orderItems + ", stripe=" + stripe + "]";
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
		DriverFoodOrder other = (DriverFoodOrder) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

}
