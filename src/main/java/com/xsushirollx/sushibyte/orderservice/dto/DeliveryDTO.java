package com.xsushirollx.sushibyte.orderservice.dto;

import com.xsushirollx.sushibyte.orderservice.model.Delivery;

public class DeliveryDTO {
	
	private Long id;
	
	private String street;

	private String city;
	
	private String state;

	private Integer zipCode;

	private String deliveryTime;
	
	private Long driverId;

	public DeliveryDTO(String street, String city, String state, Integer zipCode) {
		super();
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}
	
	public DeliveryDTO(Delivery delivery) {
		this.street = delivery.getStreet();
		this.city = delivery.getCity();
		this.state = delivery.getState();
		this.zipCode = delivery.getZipCode();
		this.deliveryTime = delivery.getDeliveryTime();
		this.driverId = delivery.getDriverId();
	}

	public DeliveryDTO() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}
}
