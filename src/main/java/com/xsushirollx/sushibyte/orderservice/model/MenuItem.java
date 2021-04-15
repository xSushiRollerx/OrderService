package com.xsushirollx.sushibyte.orderservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "food")
public class MenuItem {
	
	@Id
	@Column(name = "id")
	Integer id;
	
	@JsonIgnore
	@Column(name = "restaurant_id")
	int restaurant;
	
	@Column(name = "name")
	String name;
	
	@Column(name = "summary")
	String summary;
	
	@JsonIgnore
	@Column(name = "is_active")
	Integer isActive;
	
	@Column(name = "cost")
	Float price;
	
	@Column(name = "special")
	Integer special;
	
	public MenuItem() {}
	
	
	
	public MenuItem(Integer id, String name, int restaurant, String summary, int isActive, int special, float price) {
		super();
		this.id = id;
		this.restaurant = restaurant;
		this.name = name;
		this.summary = summary;
		this.isActive = isActive;
		this.price = price;
		this.special = special;
	}



	public float getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Integer restaurant) {
		this.restaurant = restaurant;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getSpecial() {
		return special;
	}
	public void setSpecial(int special) {
		this.special = special;
	}
	
	
	
	@Override
	public String toString() {
		return "MenuItem [id=" + id + ", restaurant=" + restaurant + ", name=" + name + ", summary=" + summary
				+ ", isActive=" + isActive + ", price=" + price + ", special=" + special + "]";
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
		MenuItem other = (MenuItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	

}
