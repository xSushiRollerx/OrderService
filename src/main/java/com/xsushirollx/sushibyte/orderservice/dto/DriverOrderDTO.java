package com.xsushirollx.sushibyte.orderservice.dto;

import java.util.ArrayList;
import java.util.List;

import com.xsushirollx.sushibyte.orderservice.model.DriverFoodOrder;
import com.xsushirollx.sushibyte.orderservice.model.OrderItem;

public class DriverOrderDTO {

	private Long id;

	private Integer state;

	private Long customerId;

	private Integer refunded;

	private List<OrderItemDTO> orderItems;

	private DeliveryDTO address;
	
	private RestaurantDTO restaurant;

	private String stripe;

	private String dateSubmitted;
	
	public DriverOrderDTO() {
	}

public DriverOrderDTO(DriverFoodOrder order) {
		
		List<OrderItemDTO> orderItems = new ArrayList<OrderItemDTO>();
		
		for (OrderItem o :  order.getOrderItems()) {
			orderItems.add(new OrderItemDTO(o));
		}
		
		this.id = order.getId();
		this.state = order.getState();
		this.customerId = order.getCustomerId();
		this.orderItems = orderItems;
		this.address = order.getAddress() == null ? null : new DeliveryDTO(order.getAddress());
		this.restaurant = order.getRestaurant() == null ? null : new RestaurantDTO(order.getRestaurant());
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

	public List<OrderItemDTO> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemDTO> orderItems) {
		this.orderItems = orderItems;
	}

	public DeliveryDTO getAddress() {
		return address;
	}

	public void setAddress(DeliveryDTO address) {
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
		DriverOrderDTO other = (DriverOrderDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public RestaurantDTO getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(RestaurantDTO restaurant) {
		this.restaurant = restaurant;
	}
	

}
