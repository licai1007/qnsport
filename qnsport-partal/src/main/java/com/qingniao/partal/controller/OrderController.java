package com.qingniao.partal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qingniao.core.common.Constants;
import com.qingniao.core.common.LocalSession;
import com.qingniao.core.pojo.cart.UserCart;
import com.qingniao.core.pojo.cart.UserItem;
import com.qingniao.core.pojo.order.Order;
import com.qingniao.core.pojo.product.Sku;
import com.qingniao.core.services.OrderBiz;
import com.qingniao.core.services.SkuBiz;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Controller
public class OrderController {
	@Autowired
	OrderBiz orderBiz;
	@Autowired
	SkuBiz skuBiz;
	@Autowired
	JedisPool jedisPool;
	@Autowired
	LocalSession localSession;
	@RequestMapping("/order/submitOrder.html")
	public String submitOrder(HttpServletRequest request,HttpServletResponse response,Order order,Model model){
		UserCart userCart = new UserCart();
		String username = localSession.getSession(request, response,Constants.USER_NAME);
		//设置订单所属用户
		order.setBuyerId(username);
		
		Jedis jedis = jedisPool.getResource();
		//加载购物车中数据
		List<String> keys = jedis.lrange("userCart:"+username,0,-1);
		for (String key : keys) {
			Sku sku = new Sku();
			sku.setId(Long.parseLong(key));
			UserItem userItem = new UserItem();
			userItem.setSku(sku);
			userItem.setAmount(Integer.parseInt(jedis.hget("userItem:"+username,key)));
			userCart.addUserItem(userItem);
		}
		
		//加载数据
		showCart(userCart);
		
		//保存订单及订单详情
		orderBiz.insertOrder(order, userCart);
		
		//修改库存
		for (String skuId : keys) {
			Sku sku = skuBiz.loadSkuById(Long.parseLong(skuId));
			String amount = jedis.hget("userItem:"+username,skuId);
			sku.setStock(sku.getStock() - Integer.parseInt(amount));
			skuBiz.updateSkuById(sku);
		}
		
		//清空购物车
		jedis.del("userCart:"+username,"userItem:"+username);
		model.addAttribute("order",order);
		return "product/confirmOrder";
	}
	/**
	 * 购物车数据加载回显
	 * @param userCart
	 */
	private void showCart(UserCart userCart) {
		List<UserItem> items = userCart.getItems();
		if(items != null){
			for (UserItem userItem : items) {
				Sku sku = skuBiz.loadSkuById(userItem.getSku().getId());
				userItem.setSku(sku);
			}
		}
		
	}
}
