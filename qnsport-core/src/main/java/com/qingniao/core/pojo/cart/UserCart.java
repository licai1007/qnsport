package com.qingniao.core.pojo.cart;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.qingniao.core.pojo.product.Sku;

/**
 * 购物车
 * @author licai
 *
 */
public class UserCart {
	private List<UserItem> items = new ArrayList<UserItem>();

	public List<UserItem> getItems() {
		return items;
	}

	public void setItems(List<UserItem> items) {
		this.items = items;
	}
	
	//把购物项添加到购物车  需要合并相同的商品
	public void addUserItem(UserItem userItem){
		//商品如果相同   数量追加
		if(items.contains(userItem)){
			for (UserItem item : items) {
				if(item.equals(userItem)){
					item.setAmount(userItem.getAmount()+item.getAmount());
				}
			}
		}else{
			items.add(userItem);
		}
		
	}
	//删除购物项
	public void delUserItem(Long skuId){
		Sku sku = new Sku();
		sku.setId(skuId);
		UserItem userItem = new UserItem();
		userItem.setSku(sku);
		items.remove(userItem);
	}
	//需要忽略以下方法转json
	//小计
	@JsonIgnore
	public Integer getAllProductAmount(){
		Integer sum = 0;
		for (UserItem userItem : items) {
			sum += userItem.getAmount();
		}
		return sum;
	}
	//金额
	@JsonIgnore
	public Double getPrice(){
		Double price = 0d;
		for (UserItem userItem : items) {
			price += userItem.getAmount()*userItem.getSku().getPrice();
		}
		return price;
	}
	//运费      金额大于99元是免运费的
	@JsonIgnore
	public Float getExtra(){
		if(getPrice() <= 99){
			return 10f;
		}else{
			return 0f;
		}
	}
	//总金额
	@JsonIgnore
	public Double getAllPrice(){
		return getPrice()+getExtra();
	}
	//清空购物车
	public void clearCart() {
		items.clear();
	}
}
