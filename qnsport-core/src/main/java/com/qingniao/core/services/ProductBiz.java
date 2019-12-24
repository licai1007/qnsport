package com.qingniao.core.services;


import com.qingniao.common.page.PageInfo;
import com.qingniao.core.pojo.product.Product;
import com.qingniao.core.pojo.product.ProductExample;

public interface ProductBiz {
	int insertSelective(Product record);
	PageInfo selectByExample(ProductExample example);
	int countByExample(ProductExample example);
	void onSave(Long[] ids);
	Product selectProductAndImgByProductId(Long id);
	void sellOut(Long[] ids);
	void optDelete(Long id,Boolean isShow);
}
