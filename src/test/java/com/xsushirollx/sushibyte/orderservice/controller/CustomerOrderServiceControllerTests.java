package com.xsushirollx.sushibyte.orderservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.xsushirollx.sushibyte.orderservice.model.Delivery;
import com.xsushirollx.sushibyte.orderservice.model.FoodOrder;
import com.xsushirollx.sushibyte.orderservice.model.OrderItem;
import com.xsushirollx.sushibyte.orderservice.security.JWTUtil;
import com.xsushirollx.sushibyte.orderservice.service.OrderService;


@AutoConfigureMockMvc
@SpringBootTest
public class CustomerOrderServiceControllerTests {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	OrderService orderService;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	JWTUtil util;
	
	@Test
	public void update204() {
		
		OrderItem o = new OrderItem();
		String token  = "Bearer " + util.generateToken("1");
		when(orderService.addOrUpdateOrderItem(Mockito.any(OrderItem.class), Mockito.anyInt())).thenReturn(true);
	
		try {
			mockMvc.perform(post("/customer/order/update").header("Authorization", token).contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(o)))
			.andExpect(status().isNoContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void update400() {
		
		OrderItem o = new OrderItem();
		String token  = "Bearer " + util.generateToken("1");
		when(orderService.addOrUpdateOrderItem(Mockito.any(OrderItem.class), Mockito.anyInt())).thenReturn(false);
	
		try {
			mockMvc.perform(post("/customer/order/update").header("Authorization", token).contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(o)))
			.andExpect(status().isBadRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void update403() {
		
		OrderItem o = new OrderItem();
		String token  = "Bearer asdkjasjkdnkasndaksdakdksj";
		when(orderService.addOrUpdateOrderItem(Mockito.any(OrderItem.class), Mockito.anyInt())).thenReturn(true);
	
		try {
			mockMvc.perform(post("/customer/order/update").header("Authorization", token).contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(o)))
			.andExpect(status().isForbidden());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void submit204() {
		
		FoodOrder o = new FoodOrder();
		o.setId(3);
		String token  = "Bearer " + util.generateToken("1");
		when(orderService.submitOrder(Mockito.any(FoodOrder.class))).thenReturn(true);
	
		try {
			mockMvc.perform(put("/customer/order/submit").header("Authorization", token).contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(o)))
			.andExpect(status().isNoContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void submit403() {
		
		FoodOrder o = new FoodOrder();
		String token  = "Bearer "+ util.generateToken("1");
		when(orderService.submitOrder(Mockito.any(FoodOrder.class))).thenReturn(true);
	
		try {
			mockMvc.perform(put("/customer/order/submit").header("Authorization", token).contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(o)))
			.andExpect(status().isForbidden());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void submit400() {
		
		FoodOrder o = new FoodOrder();
		o.setId(3);
		String token  = "Bearer " + util.generateToken("1");
		when(orderService.submitOrder(Mockito.any(FoodOrder.class))).thenReturn(false);
	
		try {
			mockMvc.perform(put("/customer/order/submit").header("Authorization", token).contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(o)))
			.andExpect(status().isBadRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void delivery204() {
		
		Delivery d = new Delivery();
		d.setId(3);
		String token  = "Bearer " + util.generateToken("1");
		when(orderService.updateDeliveryAddress(Mockito.any(Delivery.class))).thenReturn(true);
	
		try {
			mockMvc.perform(put("/customer/order/delivery").header("Authorization", token).contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(d)))
			.andExpect(status().isNoContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void delivery400() {
		
		Delivery d = new Delivery();
		d.setId(3);
		String token  = "Bearer " + util.generateToken("1");
		when(orderService.updateDeliveryAddress(Mockito.any(Delivery.class))).thenReturn(false);
	
		try {
			mockMvc.perform(put("/customer/order/delivery").header("Authorization", token).contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(d)))
			.andExpect(status().isBadRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void delivery403() {
		
		Delivery d = new Delivery();
		d.setId(7);
		String token  = "Bearer ";
		when(orderService.updateDeliveryAddress(Mockito.any(Delivery.class))).thenReturn(true);
	
		try {
			mockMvc.perform(put("/customer/order/delivery").header("Authorization", token).contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(d)))
			.andExpect(status().isForbidden());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void active200() {
		
		String token  = "Bearer " + util.generateToken("1");
		when(orderService.getActiveOrder(Mockito.anyInt())).thenReturn(new FoodOrder());
	
		try {
			mockMvc.perform(get("/customer/order/active").header("Authorization", token).contentType(MediaType.APPLICATION_JSON).content("Filler"))
			.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void active403() {
		
		String token  = "Bearer " ;
		when(orderService.getActiveOrder(Mockito.anyInt())).thenReturn(new FoodOrder());
	
		try {
			mockMvc.perform(get("/customer/order/active").header("Authorization", token).contentType(MediaType.APPLICATION_JSON).content("Filler"))
			.andExpect(status().isForbidden());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void all200() {
		
		String token  = "Bearer " + util.generateToken("1");
		when(orderService.getAllOrders(Mockito.anyInt())).thenReturn(new ArrayList<FoodOrder>());
	
		try {
			mockMvc.perform(get("/customer/order/all").header("Authorization", token).contentType(MediaType.APPLICATION_JSON).content("Filler"))
			.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void all403() {
		
		String token  = "Bearer ";
		when(orderService.getAllOrders(Mockito.anyInt())).thenReturn(new ArrayList<FoodOrder>());
	
		try {
			mockMvc.perform(get("/customer/order/all").header("Authorization", token).contentType(MediaType.APPLICATION_JSON).content("Filler"))
			.andExpect(status().isForbidden());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void delete204() {
		
		OrderItem o  = new OrderItem();
		o.setOrderId(3);
		String token  = "Bearer " + util.generateToken("1");
		when(orderService.deleteOrderItem(Mockito.any(OrderItem.class))).thenReturn(true);
	
		try {
			mockMvc.perform(delete("/customer/order/remove").header("Authorization", token).contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(o)))
			.andExpect(status().isNoContent());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void delete400() {
		
		OrderItem o  = new OrderItem();
		o.setOrderId(3);
		String token  = "Bearer " + util.generateToken("1");
		when(orderService.deleteOrderItem(Mockito.any(OrderItem.class))).thenReturn(false);
	
		try {
			mockMvc.perform(delete("/customer/order/remove").header("Authorization", token).contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(o)))
			.andExpect(status().isBadRequest());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void delete403() {
		
		OrderItem o  = new OrderItem();
		o.setOrderId(5);
		String token  = "Bearer " + util.generateToken("1");
		when(orderService.deleteOrderItem(Mockito.any(OrderItem.class))).thenReturn(true);
	
		try {
			mockMvc.perform(delete("/customer/order/remove").header("Authorization", token).contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(o)))
			.andExpect(status().isForbidden());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void cancel204() {
		
		FoodOrder o = new FoodOrder();
		o.setId(3);
		String token  = "Bearer " + util.generateToken("1");
		when(orderService.cancelOrder(Mockito.any(FoodOrder.class))).thenReturn(true);
	
		try {
			mockMvc.perform(put("/customer/order/cancel").header("Authorization", token).contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(o)))
			.andExpect(status().isNoContent());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void cancel400() {
		
		FoodOrder o = new FoodOrder();
		o.setId(3);
		String token  = "Bearer " + util.generateToken("1");
		when(orderService.cancelOrder(Mockito.any(FoodOrder.class))).thenReturn(false);
	
		try {
			mockMvc.perform(put("/customer/order/cancel").header("Authorization", token).contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(o)))
			.andExpect(status().isBadRequest());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void cancel403() {
		
		FoodOrder o = new FoodOrder();
		o.setId(5);
		String token  = "Bearer " + util.generateToken("1");
		when(orderService.cancelOrder(Mockito.any(FoodOrder.class))).thenReturn(false);
	
		try {
			mockMvc.perform(put("/customer/order/cancel").header("Authorization", token).contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(o)))
			.andExpect(status().isForbidden());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
