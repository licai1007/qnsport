package com.qingniao.core.services;

import java.util.List;

import com.qingniao.core.pojo.product.Sku;
import com.qingniao.core.pojo.product.SkuExample;

public interface SkuBiz {
	int insertSelective(Sku record);
	List<Sku> selectSkuByProductId(SkuExample skuExample);
	void updateSkuById(Sku sku);
	Sku loadSkuById(Long id);
}
