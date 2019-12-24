package com.qingniao.product;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qingniao.core.dao.product.ProductMapper;
import com.qingniao.core.pojo.product.Product;
import com.qingniao.core.pojo.product.ProductExample;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-*.xml")
public class TestProductDao {
	@Autowired
	private ProductMapper productMapper;
	//测试添加
	@Test
	public void demo1(){
		Product product = new Product();
		product.setId(3l);
		product.setName("运动裤");
		product.setDescription("质量很好！");
		productMapper.insertSelective(product);
	}
	//查询
	@Test
	public void demo2(){
		ProductExample productExample = new ProductExample();
		List<Product> products = productMapper.selectByExample(productExample);
		System.out.println(products.size());
	}
	//修改
	@Test
	public void demo3(){
		Product product = new Product();
		product.setId(3l);
		product.setName("时尚衣");
		product.setDescription("质量一般般！");
		productMapper.updateByPrimaryKeySelective(product);
	}
	//删除
	@Test
	public void demo4(){
		productMapper.deleteByPrimaryKey(3l);
	}
	
}
