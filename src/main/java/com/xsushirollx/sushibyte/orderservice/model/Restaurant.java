package com.xsushirollx.sushibyte.orderservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.xsushirollx.sushibyte.orderservice.dto.RestaurantDTO;

/**
 * @author Tsemaye
 *
 */
@Entity
@Table(name = "Restaurant")
public class Restaurant {
	
	@Id
	@Column(name = "id", updatable = false, insertable = false)
	private Long id;

	@Column(name = "name", updatable = false, insertable = false)
	private String name;

	public Restaurant () {}
	
	public Restaurant (RestaurantDTO r) {
		
		this.id = r.getId();
		this.name = r.getName();
				
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
	
	
}
