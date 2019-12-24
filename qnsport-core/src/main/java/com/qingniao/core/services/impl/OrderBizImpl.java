package com.qingniao.core.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qingniao.core.dao.order.DetailMapper;
import com.qingniao.core.dao.order.OrderMapper;
import com.qingniao.core.pojo.cart.UserCart;
import com.qingniao.core.pojo.cart.UserItem;
import com.qingniao.core.pojo.order.Detail;
import com.qingniao.core.pojo.order.Order;
import com.qingniao.core.pojo.order.OrderExample;
import com.qingniao.core.services.OrderBiz;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
@Service
@Transactional
public class OrderBizImpl implements OrderBiz {
	@Autowired
	DetailMapper detailMapper;
	@Autowired
	OrderMapper orderMapper;
	@Autowired
	JedisPool jedisPool;
	@Override
	public void insertOrder(Order order, UserCart userCart) {
		Jedis jedis = jedisPool.getResource();
		Long oid = jedis.incr("orderId");
		//订单id
		order.setId(oid);
		//金额
		order.setOrderPrice(Float.parseFloat(userCart.getPrice().toString()));
		//运费
		order.setDeliverFee(Float.parseFloat(userCart.getExtra().toString()));
		//总金额
		order.setTotalFee(Float.parseFloat(userCart.getAllPrice().toString()));
		//是否到付
		if(order.getPaymentWay() == 0){
			order.setIsPaiy(0);       //设为到付
		}else{
			order.setIsPaiy(1);       //设为待付款
		}
		//订单状态
		order.setOrderState(0);       //提交订单
		//生成时间
		order.setCreateDate(new Date());
		//保存订单
		orderMapper.insertSelective(order);
		
		//订单详情
		List<UserItem> items = userCart.getItems();
		for (UserItem userItem : items) {
			Detail detail = new Detail();
			//订单id
			detail.setOrderId(oid);
			//商品id
			detail.setProductId(userItem.getSku().getProductId());
			//商品名称
			detail.setProductName(userItem.getSku().getProduct().getName());
			//商品价格
			detail.setPrice(userItem.getSku().getPrice());
			//颜色
			detail.setColor(userItem.getSku().getColor().getName());
			//尺码
			detail.setSize(userItem.getSku().getSize());
			//数量
			detail.setAmount(userItem.getAmount());
			//保存
			detailMapper.insertSelective(detail);
		}

	}
	@Override
	public List<Order> getList(OrderExample orderExample) {
		List<Order> orders = orderMapper.selectByExample(orderExample);
		return orders;
	}
	/**
	 * 通过id查询订单
	 */
	@Override
	public Order selectOrderByKey(Long id) {
		Order order = orderMapper.selectByPrimaryKey(id);
		return order;
	}

}
