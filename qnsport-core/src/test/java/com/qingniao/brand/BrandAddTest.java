package com.qingniao.brand;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qingniao.core.pojo.Brand;
import com.qingniao.core.services.BrandBiz;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-*.xml")
public class BrandAddTest {
	@Autowired
	private BrandBiz brandBiz;
	@Test
	public void testAdd(){
		Brand brand = new Brand(1l,"安踏","是一个运动品牌","","",1);
		brandBiz.addBrand(brand);
	}
}
