package com.xsushirollx.sushibyte.orderservice.dto;

import java.util.List;

public class EventDTO {

	
	private List<FoodOrderDTO> description;
	
	private String client_secret;

	public List<FoodOrderDTO> getDescription() {
		return description;
	}

	public void setDescription(List<FoodOrderDTO> description) {
		this.description = description;
	}

	public String getClient_secret() {
		return client_secret;
	}

	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}
	
	
}
