package com.xsushirollx.sushibyte.orderservice.dto;

import com.xsushirollx.sushibyte.orderservice.model.Restaurant;

public class RestaurantDTO {
	
	private Long id;

	private String name;

	public RestaurantDTO () {}
	
	public RestaurantDTO (Restaurant r) {
		
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
