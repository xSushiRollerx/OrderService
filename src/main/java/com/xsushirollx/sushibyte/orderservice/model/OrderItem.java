package com.xsushirollx.sushibyte.orderservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xsushirollx.sushibyte.orderservice.dto.OrderItemDTO;

@Entity
@Table(name = "order_item")
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false)
	private Integer id;

	@Column(name = "food_id", updatable = false)
	private Integer foodId;

	@Column(name = "count", updatable = false)
	private Integer quantity;

	@Column(name = "price", updatable = false)
	private Float price;

	@Column(name = "food_item_name", updatable = false)
	private String name;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	FoodOrder order;
	
	public OrderItem() {}

	public OrderItem(OrderItemDTO orderItem) {
		this.id = orderItem.getId();
		this.foodId = orderItem.getFoodId();
		this.quantity = orderItem.getQuantity();
		this.price = orderItem.getPrice();
		this.name = orderItem.getName();
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFoodId() {
		return foodId;
	}

	public void setFoodId(Integer foodId) {
		this.foodId = foodId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FoodOrder getOrder() {
		return order;
	}

	public void setOrder(FoodOrder order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", foodId=" + foodId + ", orderId=" /** + orderId **/
				+ ", quantity=" + quantity + ", price=" + price + ", name=" + name + "]";
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
		OrderItem other = (OrderItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
