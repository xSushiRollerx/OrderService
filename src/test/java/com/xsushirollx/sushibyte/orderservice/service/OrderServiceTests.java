package com.xsushirollx.sushibyte.orderservice.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.EventData;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.xsushirollx.sushibyte.orderservice.dao.FoodOrderDAO;
import com.xsushirollx.sushibyte.orderservice.dto.DeliveryDTO;
import com.xsushirollx.sushibyte.orderservice.dto.FoodOrderDTO;
import com.xsushirollx.sushibyte.orderservice.dto.OrderItemDTO;
import com.xsushirollx.sushibyte.orderservice.exception.OrderServiceException;
import com.xsushirollx.sushibyte.orderservice.model.FoodOrder;


@SpringBootTest
public class OrderServiceTests {
	
	Logger log = Logger.getLogger("OrderServiceTests");
	
	@MockBean
	FoodOrderDAO fdao;

	@Autowired
	OrderService orderService;
	
	
	FoodOrderDTO order;
	
	@SuppressWarnings("deprecation")
	JsonParser parser = new JsonParser();
	
	@Autowired
	ObjectMapper mapper;
	
	private final String API_KEY = "sk_test_51Iwe6JI3Xcs3HqD58EIktgAoDmcfazeFLBufgPihXrAcbclMf9E1CzBM1OWll7Xrf0wIDyoaGUoQkFMXyOt99L5h004AmBompb";
	
	@BeforeEach
	public void setUp() {
		List<OrderItemDTO> items = new ArrayList<OrderItemDTO>();
		items.add(new OrderItemDTO((long)2, 4, (float) 3.99, "Miso Soup", (long) 5));
		items.add(new OrderItemDTO((long) 5, 2, (float) 8.99, "California Roll", (long) 5));
		items.add(new OrderItemDTO((long) 13, 1, (float) 11.99, "Teriyaki Shrimp", (long) 5));
		
		order = new FoodOrderDTO( null, 0, (long) 1, items, new DeliveryDTO());
	}
	
	
	@Test
	public void sumbitOrderHP() throws SQLIntegrityConstraintViolationException {
		when(fdao.existsByIdAndState(Mockito.anyLong(), Mockito.anyInt())).thenReturn(true);
		when(fdao.save(Mockito.any(FoodOrder.class))).thenReturn(new FoodOrder(order));
		orderService.submitOrder(order, 1);
	}
	
	@Test
	public void sumbitEventOrderHP() throws SQLIntegrityConstraintViolationException, JsonMappingException, JsonProcessingException, StripeException {
		when(fdao.existsByIdAndState(Mockito.anyLong(), Mockito.anyInt())).thenReturn(true);
		Stripe.apiKey = API_KEY;
		Event event = new Event();
		EventData data = new EventData();
		List<FoodOrderDTO> orders = new ArrayList<>();
		orders.add(order);
		PaymentIntent intent = PaymentIntent.create(PaymentIntentCreateParams.builder().setAmount((long) 1000).setCurrency("usd").setDescription( mapper.writeValueAsString(orders)).build());
		@SuppressWarnings("deprecation")
		JsonObject object = parser.parse(intent.toJson()).getAsJsonObject();
		
		data.setObject(object);
		event.setData(data);
		log.info(event.toString());
		
		orderService.submitOrder(event);
	}
	
	
	@Test
	public void getAllCustomerHP() {
		when(fdao.findByCustomerId(Mockito.anyLong())).thenReturn(new ArrayList<FoodOrder>());
		orderService.getAllCustomerOrders((long) 1);
	}
	
	@Test
	public void updateOrderStateHP() throws OrderServiceException {
		when(fdao.existsByIdAndState(Mockito.anyLong(), Mockito.anyInt())).thenReturn(true);
		when(fdao.save(Mockito.any(FoodOrder.class))).thenReturn(new FoodOrder(order));
		orderService.updateOrderState(new FoodOrderDTO((long) 2, 2, (long) 1, new ArrayList<OrderItemDTO>(), new DeliveryDTO()));
	}
	
	@Test
	public void updateOrderStateSPWrongStateSpecifiedToUpdateTo() {
		when(fdao.existsByIdAndState(Mockito.anyLong(), Mockito.anyInt())).thenReturn(false);
		assertThrows(OrderServiceException.class, () -> {orderService.updateOrderState(new FoodOrderDTO((long) 3, 2, (long) 1, new ArrayList<OrderItemDTO>(), new DeliveryDTO())); });
	}
	
	@Test
	public void updateOrderStateSPStateGreaterThan5() {
		when(fdao.existsByIdAndState(Mockito.anyLong(), Mockito.anyInt())).thenReturn(false);
		assertThrows(OrderServiceException.class, () -> {orderService.updateOrderState(new FoodOrderDTO((long) 6, 5, (long) 2, new ArrayList<OrderItemDTO>(), new DeliveryDTO())); });
	}
	
	@Test
	public void cancelOrderHP() throws OrderServiceException {
		when(fdao.existsByIdAndState(Mockito.anyLong(), Mockito.anyInt())).thenReturn(true);
		when(fdao.save(Mockito.any(FoodOrder.class))).thenReturn(new FoodOrder(order));
		orderService.cancelOrder(new FoodOrderDTO((long) 3, 0, (long) 1, new ArrayList<OrderItemDTO>(), new DeliveryDTO()));
	}
	
	@Test
	public void cancelOrderSP() {
		when(fdao.existsByIdAndState(Mockito.anyLong(), Mockito.anyInt())).thenReturn(false);
		assertThrows(OrderServiceException.class, () -> {orderService.cancelOrder(new FoodOrderDTO((long) 5, 2, (long) 2, new ArrayList<OrderItemDTO>(), new DeliveryDTO()));});
	}
	
}
