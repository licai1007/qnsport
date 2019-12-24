package com.qingniao.core.pojo.cart;

import com.qingniao.core.pojo.product.Sku;

/**
 * 购物项
 * @author licai
 *
 */
public class UserItem {
	//销售单元
	private Sku sku;
	//数量
	private Integer amount;
	//是否有货
	private Boolean isHave = true;
	public Sku getSku() {
		return sku;
	}
	public void setSku(Sku sku) {
		this.sku = sku;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Boolean getIsHave() {
		return isHave;
	}
	public void setIsHave(Boolean isHave) {
		this.isHave = isHave;
	}
	//重写hashCode和equals
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sku == null) ? 0 : sku.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserItem other = (UserItem) obj;
		if (sku == null) {
			if (other.sku != null)
				return false;
		} else if (!sku.getId().equals(other.getSku().getId()))//如果skuId相同为同款商品
			return false;
		return true;
	}
	
}
