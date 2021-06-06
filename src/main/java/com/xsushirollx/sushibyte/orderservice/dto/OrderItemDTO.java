package com.xsushirollx.sushibyte.orderservice.dto;

import com.xsushirollx.sushibyte.orderservice.model.OrderItem;

public class OrderItemDTO {

	private Long id;

	private Long foodId;

	private Integer quantity;

	private Float price;

	private String name;

	private Long restaurantId;

	public OrderItemDTO(Long foodId, Integer quantity, Float price, String name, Long restaurantId) {
		super();
		this.foodId = foodId;
		this.quantity = quantity;
		this.price = price;
		this.name = name;
		this.restaurantId = restaurantId;
	}
	
	public OrderItemDTO(OrderItem orderItem) {
		this.id = orderItem.getId();
		this.foodId = orderItem.getFoodId();
		this.quantity = orderItem.getQuantity();
		this.price = orderItem.getPrice();
		this.name = orderItem.getName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFoodId() {
		return foodId;
	}

	public void setFoodId(Long foodId) {
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
	
	public Long getRestaurantId() {
		return this.restaurantId;
	}
	
	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}


	@Override
	public String toString() {
		return "OrderItemDTO [id=" + id + ", foodId=" + foodId + ", quantity=" + quantity + ", price=" + price
				+ ", name=" + name + ", restaurantId=" + restaurantId + "]";
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
		OrderItemDTO other = (OrderItemDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
