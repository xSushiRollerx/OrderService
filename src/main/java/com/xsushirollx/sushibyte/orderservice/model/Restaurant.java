package com.xsushirollx.sushibyte.orderservice.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.xsushirollx.sushibyte.orderservice.dto.RestaurantDTO;

@Entity(name = "Restaurant")
@Table(name = "restaurant")
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", insertable = false, updatable = false)
	private Long id;

	@Column(name = "name", insertable = false, updatable = false)
	private String name;
	
	@Column(name = "street", insertable = false, updatable = false)
	private String streetAddress;

	@Column(name = "city", insertable = false, updatable = false)
	private String city;

	@Column(name = "state", insertable = false, updatable = false)
	private String state;

	@Column(name = "zip_code", insertable = false, updatable = false)
	private Integer zipCode;
	
	public Restaurant() {}
	
	public Restaurant(RestaurantDTO restaurant) {
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
		Restaurant other = (Restaurant) obj;
		return Objects.equals(id, other.id);
	}
	
}
