package com.qingniao.sku;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qingniao.core.dao.product.SkuMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-*.xml")
public class TestSku {
	@Autowired
	private SkuMapper skuMapper;
	@Test
	public void demo(){
		float price = skuMapper.selectMinPriceByProductId(10031l);
		System.out.println(price);
	}
}
