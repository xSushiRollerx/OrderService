package com.xsushirollx.sushibyte.orderservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.xsushirollx.sushibyte.orderservice.dto.DeliveryDTO;

@Entity
@Table(name = "delivery")
public class Delivery {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false)
	private Integer id;
	
	@Column(name = "street", updatable = false)
	private String street;
	
	@Column(name = "city", updatable = false)
	private String city;
	
	@Column(name = "state", updatable = false)
	private String state;
	
	@Column(name = "zip_code",updatable = false)
	private Integer zipCode;
	
	@Column(name = "delivery_time", updatable = false)
	private String deliveryTime;
	
	@OneToOne
    @MapsId
    @JoinColumn(name = "id", updatable = false)
    private FoodOrder order;
	
	public FoodOrder getOrder() {
		return order;
	}

	public void setOrder(FoodOrder order) {
		this.order = order;
	}

	public Delivery() {}

	public Delivery(String street, String city, String state, Integer zipCode) {
		super();
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}
	
	public Delivery(DeliveryDTO delivery) {
		this.street = delivery.getStreet();
		this.city = delivery.getCity();
		this.state = delivery.getState();
		this.zipCode = delivery.getZipCode();
		this.deliveryTime = delivery.getDeliveryTime();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
	
	
	

}
