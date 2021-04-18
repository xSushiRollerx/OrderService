//package com.xsushirollx.sushibyte.orderservice.controller;
//
//import static org.mockito.Mockito.when;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import com.xsushirollx.sushibyte.orderservice.model.OrderItem;
//import com.xsushirollx.sushibyte.orderservice.service.OrderService;
//
//@WebMvcTest(OrderServiceController.class)
//public class OrderServiceController {
//
//	@Autowired
//	MockMvc mockMvc;
//	
//	@MockBean
//	OrderService orderService;
//	
//	@Test
//	public void updateEndpointHP() {
//		
//		when(orderService.addOrUpdateOrderItem(new OrderItem(), 96)).thenReturn(true);
//		
//		this.mockMvc.perform(MockMvcRequestBuilders.get("/update")).andExpect(status().isOk());
//	}
//	
//}
