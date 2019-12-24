package com.qingniao.console.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qingniao.core.pojo.order.Detail;
import com.qingniao.core.pojo.order.Order;
import com.qingniao.core.pojo.order.OrderExample;
import com.qingniao.core.pojo.order.OrderExample.Criteria;
import com.qingniao.core.pojo.user.Addr;
import com.qingniao.core.services.AddrBiz;
import com.qingniao.core.services.OrderBiz;
import com.qingniao.core.services.OrderDetailBiz;

@Controller
public class OrderController {
	@Autowired
	OrderDetailBiz orderDetailBiz;
	@Autowired
	AddrBiz addrBiz;
	@Autowired
	OrderBiz orderBiz;
	@RequestMapping("/order/list.do")
	public String list(Model model,Integer isPaiy,Integer orderState){
		OrderExample orderExample = new OrderExample();
		Criteria criteria = orderExample.createCriteria();
		if(isPaiy != null){
			criteria.andIsPaiyEqualTo(isPaiy);
		}
		if(orderState != null){
			criteria.andOrderStateEqualTo(orderState);
		}
		List<Order> orders = orderBiz.getList(orderExample);
		model.addAttribute("orders",orders);
		return "order/list";
	}
	/**
	 * 订单详情查看
	 * @return
	 */
	@RequestMapping("/order/view.do")
	public String orderDetail(Long id,Model model){
		//查询订单信息
		Order order = orderBiz.selectOrderByKey(id);
		//地址信息
		Addr addr = addrBiz.selectAddrByUsername(order.getBuyerId());
		//订单详情信息
		List<Detail> details = orderDetailBiz.getOrderDetailByOrderId(id);
		model.addAttribute("order",order);
		model.addAttribute("addr",addr);
		model.addAttribute("details",details);
		return "order/view";
	}
}
