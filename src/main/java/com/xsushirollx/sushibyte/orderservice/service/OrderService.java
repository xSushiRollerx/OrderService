package com.xsushirollx.sushibyte.orderservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xsushirollx.sushibyte.orderservice.dao.FoodOrderDAO;
import com.xsushirollx.sushibyte.orderservice.dao.MenuItemDAO;
import com.xsushirollx.sushibyte.orderservice.dao.OrderItemDAO;
import com.xsushirollx.sushibyte.orderservice.model.FoodOrder;
import com.xsushirollx.sushibyte.orderservice.model.MenuItem;
import com.xsushirollx.sushibyte.orderservice.model.OrderItem;

@Service
public class OrderService {

	@Autowired
	OrderItemDAO odao;

	@Autowired
	FoodOrderDAO fodao;

	@Autowired
	MenuItemDAO mdao;

	public boolean addOrUpdateOrderItem(OrderItem o, int customerId) {
		try {
			o.setId(odao.findByOrderIdAndFoodId(fodao.findByCustomerIdAndState(customerId, 0).getId(), o.getFoodId())
					.getId());

			o.setOrderId(fodao.findByCustomerIdAndState(customerId, 0).getId());
			odao.save(o);
		} catch (NullPointerException e) {
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
		} catch (NullPointerException e) {
			return false;
		}
	}

	public boolean submitOrder(FoodOrder order, int customerId) {
		try {
			// UPDATE FOODORDER SO DELIVERY IS GONE AND ADDRESS IS ADDED TO FOOD ORDER ALONG
			// W/ DRIVERID
			List<OrderItem> updated = updatedOrderItem(order.getOrderItems());
			for (int i = 0; i < updated.size(); i++) {
				addOrUpdateOrderItem(updated.get(i), customerId);
			}
		} catch (NullPointerException e) {
			return false;
		}

		return updateOrderState(order) && createOrder(customerId);
	}

	public FoodOrder getActiveOrder(int userId) {
		try {

			FoodOrder order = fodao.findByCustomerIdAndState(userId, 0);
			order.setOrderItems(updatedOrderItem(order.getOrderItems()));
			return order;
		} catch (NullPointerException e) {
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

		} catch (NullPointerException e) {
			return null;
		}
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
		} catch (NullPointerException e) {
			return false;
		}

	}

	private OrderItem updatedOrderItem(OrderItem item) {
		try {
			MenuItem mitem = mdao.findById(item.getFoodId()).get();
			item.setName(mitem.getName());
			item.setIsActive(mitem.getIsActive());
			item.setPrice(mitem.getPrice());

		} catch (NullPointerException e) {
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
