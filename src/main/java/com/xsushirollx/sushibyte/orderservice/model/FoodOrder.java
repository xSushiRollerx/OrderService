package com.xsushirollx.sushibyte.orderservice.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "food_order")
public class FoodOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false)
	private Integer id;

	@Column(name = "order_state")
	private Integer state;

	@Column(name = "customer_id", updatable = false)
	private Integer customerId;

	@Column(name = "is_refunded")
	private Integer refunded;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> orderItems;

	@OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
	private Delivery address;

	@JsonIgnore
	@Column(name = "stripe")
	private Integer stripe;

	public FoodOrder() {
	}

	public FoodOrder(Integer id, Integer state) {
		this.id = id;
		this.state = state;
	}

	public FoodOrder(Integer id, Integer state, Integer customerId, List<OrderItem> orderItems, Delivery address) {
		super();
		this.id = id;
		this.state = state;
		this.customerId = customerId;
		this.orderItems = orderItems;
		this.address = address;
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

	public Delivery getAddress() {
		return address;
	}

	public void setAddress(Delivery address) {
		this.address = address;
	}

	public Integer getStripe() {
		return stripe;
	}

	public void setStripe(Integer stripe) {
		this.stripe = stripe;
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
		FoodOrder other = (FoodOrder) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
