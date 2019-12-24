package com.qingniao.console.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 初始化
 * @author licai
 *
 */
@Controller
public class IndexController {
	@RequestMapping("/index.do")
	public String index(){
		return "index";
	}
	@RequestMapping("/top.do")
	public String top(){
		return "top";
	}
	@RequestMapping("/main.do")
	public String main(){
		return "main";
	}
	@RequestMapping("/left.do")
	public String left(){
		return "left";
	}
	@RequestMapping("/right.do")
	public String right(){
		return "right";
	}
	@RequestMapping("/productMain.do")
	public String productMain(){
		return "product/product_main";
	}
	@RequestMapping("/productLeft.do")
	public String productLeft(){
		return "product/product_left";
	}
	//跳转到订单主页面
	@RequestMapping("/orderMain.do")
	public String orderMain(){
		return "order/order_main";
	}
	@RequestMapping("/orderLeft.do")
	public String orderLeft(){
		return "order/order_left";
	}
}

