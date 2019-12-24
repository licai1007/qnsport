package com.qingniao.core.services;

import java.util.List;

import com.qingniao.core.pojo.cart.UserCart;
import com.qingniao.core.pojo.order.Order;
import com.qingniao.core.pojo.order.OrderExample;

/**
 * 订单模块
 * @author licai
 *
 */
public interface OrderBiz {
	public void insertOrder(Order order,UserCart userCart);

	public List<Order> getList(OrderExample orderExample);

	public Order selectOrderByKey(Long id);
}
