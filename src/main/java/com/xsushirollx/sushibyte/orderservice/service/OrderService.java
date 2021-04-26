package com.xsushirollx.sushibyte.orderservice.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.xsushirollx.sushibyte.orderservice.dao.*;
import com.xsushirollx.sushibyte.orderservice.model.*;


@Service
public class OrderService {

	@Autowired
	OrderItemDAO odao;

	@Autowired
	FoodOrderDAO fodao;

	@Autowired
	MenuItemDAO mdao;
	
	@Autowired
	DeliveryDAO ddao;
	
	@Autowired
	CustomerDAO cdao;

	private Logger log = Logger.getLogger("OrderService");

	public boolean addOrUpdateOrderItem(OrderItem o, int customerId) {
		o.setOrderId(fodao.findByCustomerIdAndState(customerId, 0).getId());

		try {
			o.setId(odao.findByOrderIdAndFoodId(fodao.findByCustomerIdAndState(customerId, 0).getId(), o.getFoodId())
					.getId());
			odao.save(o);
		} catch (NoSuchElementException | NullPointerException e) {
			odao.save(o);
		}
		return true;
	}
	
	public boolean addOrUpdateOrderItem(OrderItem o) {

		try {
			o.setId(odao.findByOrderIdAndFoodId(o.getOrderId(), o.getFoodId())
					.getId());
			odao.save(o);
		} catch (NoSuchElementException | NullPointerException e) {
			odao.save(o);
		}
		return true;
	}

	public boolean createOrder(int customerId) {
		FoodOrder newOrder = new FoodOrder();
		if (!fodao.existsByCustomerIdAndState(customerId, 0)) {

			newOrder.setState(0);
			newOrder.setCustomerId(customerId);
			fodao.save(newOrder);
			return true;
		} else {
			return false;
		}
	}

	public boolean updateOrderState(FoodOrder order) {

		try {
			//may delete or private customer doesn't need access
			// makes sure specified order state matches the one in the db and is allowed to
			// updates
			if (fodao.findById(order.getId()).get().getState() == order.getState()
					&& fodao.findById(order.getId()).get().getState() < 5) {
				order.setState(order.getState() + 1);
				fodao.save(order);
				return true;
			} else {
				return false;
			}
		} catch (NoSuchElementException | NullPointerException e) {
			return false;
		}
	}
	
	public boolean updateDeliveryAddress(Delivery address) {
		try {
			if (fodao.findById(address.getId()).get().getState() == 0) {
				ddao.save(address);
				return true;
			} else {
				return false;
			}
		} catch (NoSuchElementException | NullPointerException e) {
			return false;
		}
	}

	public boolean submitOrder(FoodOrder order) {
		try {
			if (fodao.findById(order.getId()).get().getState() != 0 | fodao.findById(order.getId()).get().getOrderItems().size() == 0) {
				return false;
			}
			List<OrderItem> items = updatedOrderItem(fodao.findById(order.getId()).get().getOrderItems());
			for (int i = 0; i < items.size(); i++) {
				OrderItem o = items.get(i);
				o.setOrderId(order.getId());
				log.log(Level.INFO, "OrderItem Before Submission: " + o.toString());
				odao.save(o);
			}
		} catch (NoSuchElementException | NullPointerException e) {
			return false;
		}

		return updateOrderState(order) && createOrder(order.getCustomerId());
	}

	public FoodOrder getActiveOrder(int userId) {
		try {

			FoodOrder order = fodao.findByCustomerIdAndState(userId, 0);
			order.setOrderItems(updatedOrderItem(order.getOrderItems()));
			return order;
		} catch (NoSuchElementException | NullPointerException e) {
			createOrder(userId);
			return fodao.findByCustomerIdAndState(userId, 0);
		}
	}

	public List<FoodOrder> getAllOrders(int userId) {
		try {

			List<FoodOrder> orders = fodao.findByCustomerId(userId);
			for (int i = 0; i < orders.size(); i++) {
				orders.get(i).setOrderItems(updatedOrderItem(orders.get(i).getOrderItems()));
			}
			return orders;

		} catch (NoSuchElementException | NullPointerException e) {
			return null;
		}
	}
	
	public List<FoodOrder> searchAllOrders(Map<String, String> params) {
			
		List<FoodOrder> resultOrders = new ArrayList<FoodOrder>();
		
	
		for (int i = 0; i < (params.containsKey("customer") ? 1 : fodao.findAll(PageRequest.of(i, 100)).getTotalPages()); i++) {
			List<FoodOrder> currentItems;
			if (params.containsKey("customer")) {
				currentItems = searchByUser(params.get("customer"));
			} else {
				currentItems = fodao.findAll(PageRequest.of(i, 100)).toList();
			}
				
			if (params.containsKey("state")) {
				log.log(Level.INFO, "Order Service: " +  currentItems.toString());	
				currentItems = Arrays.asList(currentItems.stream().filter(o -> o.getState() == Integer.parseInt(params.get("state"))).toArray(FoodOrder[]::new));
				log.log(Level.INFO, "Order Service: " +  currentItems.toString());						
			}
			
			if (params.containsKey("refunded")) {
		
				currentItems = Arrays.asList(currentItems.stream().filter(o -> o.getRefunded() != null && o.getRefunded() == Integer.parseInt(params.get("refunded"))).toArray(FoodOrder[]::new));
						
			}
			
			if (params.containsKey("before")) {
				currentItems = Arrays.asList(currentItems.stream().filter(o ->{
					return Date.valueOf(params.get("before")).before(
							Date.valueOf(ddao.findById(o.getId()).get().getDeliveryTime())
							);
					
				}).toArray(FoodOrder[]::new));
				
			}
			
			if (params.containsKey("after")) {
				currentItems = Arrays.asList(currentItems.stream().filter(o ->{
							return Date.valueOf(params.get("after")).after(
									Date.valueOf(ddao.findById(o.getId()).get().getDeliveryTime())
									);
							
						}).toArray(FoodOrder[]::new));
			}
			resultOrders.addAll(currentItems);
			
		}
		return resultOrders;
	}
	
	private List<FoodOrder> searchByUser(String customer) {
		try {
		if(cdao.existsById(Integer.parseInt(customer))) {
			return cdao.findById(Integer.parseInt(customer)).get().getOrders();
		};
			
		} catch (NumberFormatException | NullPointerException | NoSuchElementException e) {}
		
		if (cdao.existsByUsernameAndRole(customer, 1)) {
			return cdao.findByUsername(customer).getOrders();
			
		}
		
		if (cdao.existsByEmailAndRole(customer, 1)) {
			return cdao.findByEmail(customer).getOrders();
		}
		
		return new ArrayList<FoodOrder>();
		
	}

	public boolean deleteOrderItem(OrderItem item) {
		if (odao.existsById(item.getId())) {
			odao.deleteById(item.getId());
			return true;
		} else {
			return false;
		}
	}

	public boolean cancelOrder(FoodOrder order) {

		// SHOULD THIS CALL PAYMENT API OR OPPOSITE?

		// makes sure order exists & specified order state matches the one in the db and
		// it is allowed to updates
		try {
			if (fodao.findById(order.getId()).get().getState() == 1) {
				order.setState(6);
				fodao.save(order);
				return true;
			} else {
				return false;
			}
		} catch (NoSuchElementException | NullPointerException e) {
			return false;
		}

	}
//<------------------------------------------ HELPER METHODS ------------------------------------------------------>

	private OrderItem updatedOrderItem(OrderItem item) {
		try {
			MenuItem mitem = mdao.findById(item.getFoodId()).get();
			item.setName(mitem.getName());
			item.setIsActive(mitem.getIsActive());
			item.setPrice(mitem.getPrice());

		} catch (NoSuchElementException | NullPointerException e) {
			item.setIsActive(2);
		} 	
		return item;

	}

	private List<OrderItem> updatedOrderItem(List<OrderItem> orderList) {
		if (orderList == null)
			return orderList;

		List<OrderItem> updatedList = new ArrayList<>();
		for (int i = 0; i < orderList.size(); i++) {
			updatedList.add(updatedOrderItem(orderList.get(i)));
		}
		return updatedList;
	}
}
