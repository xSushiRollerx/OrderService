package com.xsushirollx.sushibyte.orderservice.dto;

import java.util.Objects;

import com.xsushirollx.sushibyte.orderservice.model.Restaurant;

public class RestaurantDTO {

	private Long id;

	private String name;
	
	private String streetAddress;

	private String city;

	private String state;

	private Integer zipCode;
	
	
	public RestaurantDTO(Restaurant restaurant) {
		this.city = restaurant.getCity();
		this.id = restaurant.getId();
		this.name =  restaurant.getName();
		this.state = restaurant.getState();
		this.streetAddress = restaurant.getStreetAddress();
		this.zipCode = restaurant.getZipCode();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
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

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RestaurantDTO other = (RestaurantDTO) obj;
		return Objects.equals(id, other.id);
	}
	
}
