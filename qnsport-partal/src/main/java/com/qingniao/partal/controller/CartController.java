package com.qingniao.partal.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qingniao.core.common.Constants;
import com.qingniao.core.common.LocalSession;
import com.qingniao.core.pojo.cart.UserCart;
import com.qingniao.core.pojo.cart.UserItem;
import com.qingniao.core.pojo.product.Sku;
import com.qingniao.core.pojo.user.Addr;
import com.qingniao.core.services.AddrBiz;
import com.qingniao.core.services.SkuBiz;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Controller
public class CartController {
	@Autowired
	private AddrBiz addrBiz;
	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private LocalSession localSession;
	@Autowired
	private SkuBiz skuBiz;
	private ObjectMapper om = new ObjectMapper();
	@RequestMapping("/cart/shopping.html")
	public String cart(Long skuId,Integer amount,Model model,HttpServletRequest request,HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException{
		om.setSerializationInclusion(Inclusion.NON_NULL);//忽略null值
		UserCart userCart = null;
		Cookie[] cookies = request.getCookies();
		//到cookie中查找购物车
		userCart = findUserCartByCookie(cookies);
		//如果购物车为空
		if(userCart == null){
			userCart = new UserCart();
		}
		String username = localSession.getSession(request, response,Constants.USER_NAME);
		if(username != null){
			//用户登录情况下
			Jedis jedis = jedisPool.getResource();
			//将cookie中的数据保存到redis中
			List<UserItem> items = userCart.getItems();
			for (UserItem userItem : items) {
				if(jedis.hexists("userItem:"+username,userItem.getSku().getId().toString())){
					//如果存在相同商品则数量追加
					jedis.hincrBy("userItem:"+username,userItem.getSku().getId().toString(),userItem.getAmount());
				}else{
					//保存购物项
					jedis.lpush("userCart:"+username,userItem.getSku().getId().toString());
					//保存购物项中数据
					jedis.hset("userItem:"+username,userItem.getSku().getId().toString(),userItem.getAmount().toString());
				}
			}
			
			//清空购物车和cookie
			userCart.clearCart();
			clearCookie(response);
			
			//把当前商品添加到redis中
			if(skuId != null){
				if(jedis.hexists("userItem:"+username,skuId.toString())){
					//如果存在相同商品则数量追加
					jedis.hincrBy("userItem:"+username,skuId.toString(),amount);
				}else{
					jedis.lpush("userCart:"+username,skuId.toString());
					jedis.hset("userItem:"+username,skuId.toString(),amount.toString());
				}
			}
			
			//把redis中数据加载到购物车
			List<String> skuIds = jedis.lrange("userCart:"+username,0,-1);
			for (String id : skuIds) {
				Sku sku = new Sku();
				sku.setId(Long.parseLong(id));
				UserItem userItem = new UserItem();
				userItem.setSku(sku);
				String num = jedis.hget("userItem:"+username,id);
				userItem.setAmount(Integer.parseInt(num));
				userCart.addUserItem(userItem);
			}
			
		}else{
			//用户非登录情况下
			if(skuId != null){
				//把商品添加到购物车
				userItemToCart(userCart,skuId,amount,response);
			}
		}
		
		//显示购物车数据，需要把购物车数据全部加载出来，然后到页面显示
		showCart(userCart);
		
		//对购物车商品进行排序
		Collections.sort(userCart.getItems(),new Comparator<UserItem>(){
			//进行倒序
			@Override
			public int compare(UserItem o1, UserItem o2) {
				return -1;
			}
			
		});
		
		model.addAttribute("userCart",userCart);
		return "product/cart";
	}
	/**
	 * 清空cookie
	 * @param response
	 */
	private void clearCookie(HttpServletResponse response) {
		Cookie cookie = new Cookie(Constants.USER_CART,null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	//从cookie中查找购物车
	private UserCart findUserCartByCookie(Cookie[] cookies) throws JsonParseException, JsonMappingException, IOException {
		if(cookies != null && cookies.length >0){
			for (Cookie cookie : cookies) {
				if(Constants.USER_CART.equals(cookie.getName())){
					String value = cookie.getValue();
					return om.readValue(value,UserCart.class);
				}
			}
		}
		return null;
	}
	private void userItemToCart(UserCart userCart, Long skuId, Integer amount, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		//创建sku
		Sku sku = new Sku();
		sku.setId(skuId);
		
		//创建userItem
		UserItem userItem = new UserItem();
		userItem.setSku(sku);
		userItem.setAmount(amount);
		
		//添加进购物车
		userCart.addUserItem(userItem);
		
		om.setSerializationInclusion(Inclusion.NON_NULL);
		StringWriter sw = new StringWriter();
		om.writeValue(sw, userCart);//把购物车转换成json字符
		
		//回写浏览器
		Cookie cookie = new Cookie(Constants.USER_CART,sw.toString());
		cookie.setMaxAge(60*60*24*7);//保存一周
		cookie.setPath("/");
		response.addCookie(cookie);
		
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
	/**
	 * 删除购物车中的商品
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping("/delete/userItem.html")
	public String delUserItem(Long skuId,HttpServletRequest request,HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException{
		String username = localSession.getSession(request, response,Constants.USER_NAME);
		Jedis jedis = jedisPool.getResource();
		if(username != null){
			jedis.lrem("userCart:"+username,0,skuId.toString());//第二项为0，表示删除所有
			jedis.hdel("userItem:"+username,skuId.toString());
		}else{
			om.setSerializationInclusion(Inclusion.NON_NULL);
			UserCart userCart = null;
			Cookie[] cookies = request.getCookies();
			userCart = findUserCartByCookie(cookies);
			userCart.delUserItem(skuId);
			userItemToCart(userCart,response);
		}
		
		return "redirect:/cart/shopping.html";
	}
	private void userItemToCart(UserCart userCart,HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException{
		om.setSerializationInclusion(Inclusion.NON_NULL);
		StringWriter w = new StringWriter();
		om.writeValue(w,userCart);
		//写回浏览器
		Cookie cookie = new Cookie(Constants.USER_CART,w.toString());
		cookie.setMaxAge(60*60*24*7);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	/**
	 * 购物项数量追加
	 * @param skuId
	 * @param amount
	 * @param request
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping("/addUserItemAmount.html")
	public void addUserItemAmount(Long skuId,Integer amount,HttpServletRequest request,HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException{
		Jedis jedis = jedisPool.getResource(); 
		UserCart userCart = new UserCart();
		String username = localSession.getSession(request, response,Constants.USER_NAME);
		if(username != null){
			jedis.hincrBy("userItem:"+username,skuId.toString(),amount);
			List<String> skuIds = jedis.lrange("userCart:"+username,0,-1);
			for (String id : skuIds) {
				Sku sku = new Sku();
				sku.setId(Long.parseLong(id));
				UserItem userItem = new UserItem();
				userItem.setSku(sku);
				String num = jedis.hget("userItem:"+username,id);
				userItem.setAmount(Integer.parseInt(num));
				userCart.addUserItem(userItem);
			}
		}else{
			om.setSerializationInclusion(Inclusion.NON_NULL);
			userCart = findUserCartByCookie(request.getCookies());
			//如果为同款商品数量追加
			userItemToCart(userCart, skuId, amount, response);
		}
		
		showCart(userCart);
		//重新计算   小计    金额     运费   总金额
		JSONObject jo = new JSONObject();
		jo.put("allProductAmount",userCart.getAllProductAmount());
		jo.put("price",userCart.getPrice());
		jo.put("extra",userCart.getExtra());
		jo.put("allPrice",userCart.getAllPrice());
		response.getWriter().write(jo.toString());
	}
	/**
	 * 清空购物车
	 * @param response
	 * @return
	 */
	@RequestMapping("/cart/clearCart.html")
	public String clearCart(HttpServletRequest request,HttpServletResponse response){
		String username = localSession.getSession(request, response,Constants.USER_NAME);
		if(username != null){
			//清空购物车和购物项
			Jedis jedis = jedisPool.getResource();
			jedis.del("userCart:"+username);
			jedis.del("userItem:"+username);
		}
		//清空cookie
		Cookie cookie = new Cookie(Constants.USER_CART,null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		return "redirect:/cart/shopping.html";
	}
	/**
	 * 结算
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/buy/balanceAccounts.html")
	public String balanceAccounts(HttpServletRequest request,HttpServletResponse response,Model model){
		UserCart userCart = new UserCart();
		String username = localSession.getSession(request, response,Constants.USER_NAME);
		Jedis jedis = jedisPool.getResource();
		
		Boolean flag = true;
		List<String> keys = jedis.lrange("userCart:"+username,0,-1);
		if(keys != null && keys.size() != 0){
			//如果购物车不为空
			for (String key : keys) {
				Sku sku = new Sku();
				sku.setId(Long.parseLong(key));
				UserItem userItem = new UserItem();
				userItem.setSku(sku);
				String amount = jedis.hget("userItem:"+username,key);
				userItem.setAmount(Integer.parseInt(amount));
				
				//是否有货     购买数量是否小于库存
				Sku s = skuBiz.loadSkuById(Long.parseLong(key));
				if(s.getStock() < userItem.getAmount()){
					userItem.setIsHave(false);
					flag = false;
				}
				userCart.addUserItem(userItem);
			}
			showCart(userCart);
			
			if(flag){
				//有货需要通过用户名加载地址信息
				Addr addr = addrBiz.selectAddrByUsername(username);
				model.addAttribute("addr",addr);
				model.addAttribute("userCart",userCart);
				return "product/productOrder";
			}else{
				//没货
				model.addAttribute("userCart",userCart);
				return "product/cart";
			}
			
		}else{
			//如果购物车为空
			return "redirect:/cart/shopping.html";
		}
	}
}
