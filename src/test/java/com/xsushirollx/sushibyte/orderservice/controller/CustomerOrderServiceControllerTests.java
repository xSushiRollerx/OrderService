//package com.xsushirollx.sushibyte.orderservice.controller;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.xsushirollx.sushibyte.orderservice.model.OrderItem;
//import com.xsushirollx.sushibyte.orderservice.service.OrderService;
//
//
//@AutoConfigureMockMvc
//@SpringBootTest
//public class CustomerOrderServiceControllerTests {
//
//	@Autowired
//	MockMvc mockMvc;
//	
//	@MockBean
//	OrderService orderService;
//	
//	@Autowired
//	ObjectMapper objectMapper;
//	
//	@Test
//	public void updateEndpointHP() {
//		
//		OrderItem o = new OrderItem();
//		when(orderService.addOrUpdateOrderItem(Mockito.any(OrderItem.class), Mockito.anyInt())).thenReturn(true);
//	
//		try {
//			mockMvc.perform(post("/customer/order/update").contentType(MediaType.APPLICATION_JSON)
//					.content(objectMapper.writeValueAsString(o)))
//			.andExpect(status().isNoContent());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//}
