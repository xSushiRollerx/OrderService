package com.xsushirollx.sushibyte.orderservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xsushirollx.sushibyte.orderservice.dto.FoodOrderDTO;
import com.xsushirollx.sushibyte.orderservice.security.JWTUtil;
import com.xsushirollx.sushibyte.orderservice.service.OrderService;


@AutoConfigureMockMvc
@SpringBootTest
public class OrderServiceControllerTests {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	OrderService orderService;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	JWTUtil util;
	
	@Test
	public void postOrder204() {
		
		FoodOrderDTO o = new FoodOrderDTO();
		String token  = "Bearer " + util.generateToken("1");
		when(orderService.submitOrder(Mockito.any(FoodOrderDTO.class), Mockito.anyInt())).thenReturn(true);
	
		try {
			mockMvc.perform(post("/customer/1/order").header("Authorization", token).contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(o)))
					.andExpect(status().isNoContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void postOrder403() {
		
		FoodOrderDTO o = new FoodOrderDTO();
		String token  = "Bearer " + util.generateToken("1");
		when(orderService.submitOrder(Mockito.any(FoodOrderDTO.class), Mockito.anyInt())).thenReturn(true);
	
		try {
			mockMvc.perform(post("/customer/2/order").header("Authorization", token).contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(o)))
					.andExpect(status().isForbidden());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void postOrder500() {
		
		FoodOrderDTO o = new FoodOrderDTO();
		String token  = "Bearer " + util.generateToken("1");
		when(orderService.submitOrder(Mockito.any(FoodOrderDTO.class), Mockito.anyInt())).thenThrow(NumberFormatException.class);
	
		try {
			mockMvc.perform(post("/customer/1/order").header("Authorization", token).contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(o)))
					.andExpect(status().isInternalServerError());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateOrder204() {
		
		FoodOrderDTO o = new FoodOrderDTO();
		String token  = "Bearer " + util.generateToken("4");
		when(orderService.updateOrderState(Mockito.any(FoodOrderDTO.class))).thenReturn(true);
	
		try {
			mockMvc.perform(put("/customer/1/order/3").header("Authorization", token).contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(o)))
					.andExpect(status().isNoContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateOrder400() {
		
		FoodOrderDTO o = new FoodOrderDTO();
		String token  = "Bearer " + util.generateToken("4");
		when(orderService.updateOrderState(Mockito.any(FoodOrderDTO.class))).thenReturn(false);
	
		try {
			mockMvc.perform(put("/customer/1/order/3").header("Authorization", token).contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(o)))
					.andExpect(status().isBadRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateOrder500() {
		
		FoodOrderDTO o = new FoodOrderDTO();
		String token  = "Bearer " + util.generateToken("4");
		when(orderService.updateOrderState(Mockito.any(FoodOrderDTO.class))).thenThrow(NumberFormatException.class);
	
		try {
			mockMvc.perform(put("/customer/1/order/3").header("Authorization", token).contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(o)))
					.andExpect(status().isInternalServerError());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateOrder403() {
		
		FoodOrderDTO o = new FoodOrderDTO();
		String token  = "Bearer " + util.generateToken("1");
		when(orderService.updateOrderState(Mockito.any(FoodOrderDTO.class))).thenReturn(false);
	
		try {
			mockMvc.perform(put("/customer/1/order/5").header("Authorization", token).contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(o)))
					.andExpect(status().isForbidden());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void cancelOrder204() {
		
		FoodOrderDTO o = new FoodOrderDTO();
		String token  = "Bearer " + util.generateToken("4");
		when(orderService.cancelOrder(Mockito.any(FoodOrderDTO.class))).thenReturn(true);
	
		try {
			mockMvc.perform(delete("/customer/1/order/3").header("Authorization", token).contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(o)))
					.andExpect(status().isNoContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void cancelOrder400() {
		
		FoodOrderDTO o = new FoodOrderDTO();
		String token  = "Bearer " + util.generateToken("2");
		when(orderService.cancelOrder(Mockito.any(FoodOrderDTO.class))).thenReturn(false);
	
		try {
			mockMvc.perform(delete("/customer/2/order/4").header("Authorization", token).contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(o)))
					.andExpect(status().isBadRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void cancelOrder500() {
		
		FoodOrderDTO o = new FoodOrderDTO();
		String token  = "Bearer " + util.generateToken("2");
		when(orderService.cancelOrder(Mockito.any(FoodOrderDTO.class))).thenThrow(NumberFormatException.class);
	
		try {
			mockMvc.perform(delete("/customer/2/order/4").header("Authorization", token).contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(o)))
					.andExpect(status().isInternalServerError());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void cancelOrder403() {
		
		FoodOrderDTO o = new FoodOrderDTO();
		String token  = "Bearer " + util.generateToken("2");
		when(orderService.cancelOrder(Mockito.any(FoodOrderDTO.class))).thenReturn(false);
	
		try {
			mockMvc.perform(delete("/customer/2/order/3").header("Authorization", token).contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(o)))
					.andExpect(status().isForbidden());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Test
	public void getAllOrders200() {
		
		String token  = "Bearer " + util.generateToken("1");
		when(orderService.getAllCustomerOrders(Mockito.anyLong())).thenReturn(new ArrayList<FoodOrderDTO>());
	
		try {
			mockMvc.perform(get("/customer/1/orders").header("Authorization", token).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getAllOrders500() {
		
		String token  = "Bearer " + util.generateToken("1");
		when(orderService.getAllCustomerOrders(Mockito.anyLong())).thenThrow(NumberFormatException.class);
	
		try {
			mockMvc.perform(get("/customer/1/orders").header("Authorization", token).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getAllOrders403() {
		String token  = "Bearer " + util.generateToken("1");
		when(orderService.getAllCustomerOrders(Mockito.anyLong())).thenReturn(new ArrayList<FoodOrderDTO>());
	
		try {
			mockMvc.perform(get("/customer/2/orders").header("Authorization", token).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
