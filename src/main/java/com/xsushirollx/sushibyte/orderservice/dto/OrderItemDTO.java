package com.xsushirollx.sushibyte.orderservice.dto;


public class OrderItemDTO {

	private Integer id;

	private Integer foodId;

	private Integer quantity;

	private Float price;

	private String name;

	private Integer restaurantId;

	public OrderItemDTO(Integer foodId, Integer quantity, Float price, String name, Integer restaurantId) {
		super();
		this.foodId = foodId;
		this.quantity = quantity;
		this.price = price;
		this.name = name;
		this.restaurantId = restaurantId;
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
	
	public Integer getRestaurantId() {
		return this.restaurantId;
	}
	
	public void setRestaurantId(Integer restaurantId) {
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
