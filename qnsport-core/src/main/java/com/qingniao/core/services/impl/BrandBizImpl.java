package com.qingniao.core.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qingniao.common.page.PageInfo;
import com.qingniao.core.dao.BrandDao;
import com.qingniao.core.pojo.Brand;
import com.qingniao.core.pojo.BrandExample;
import com.qingniao.core.services.BrandBiz;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
@Service
@Transactional
public class BrandBizImpl implements BrandBiz {
	@Autowired
	private BrandDao brandDao;
	@Autowired
	private JedisPool jedisPool;
	@Override
	public void addBrand(Brand brand) {
		brandDao.addBrand(brand);
		//将数据保存到redis中
		Jedis jedis = jedisPool.getResource();
		//保存id
		jedis.hset("brand"+brand.getId(),"id",brand.getId().toString());
		//保存name
		jedis.hset("brand"+brand.getId(),"name",brand.getName());
		jedis.close();
	}
	@Override
	public PageInfo getBrandExample(BrandExample brandExample) {
		Integer totalCount = brandDao.count(brandExample);
		PageInfo pageInfo = new PageInfo(brandExample.getPageNo(),brandExample.getPageSize(), totalCount);
		brandExample.setPageNo(pageInfo.getPageNo());//矫正当前页
		List<BrandExample> brands = brandDao.getBrandExample(brandExample);
		pageInfo.setList(brands);
		return pageInfo;
	}
	@Override
	public void batchDelete(Long[] ids) {
		brandDao.batchDelete(ids);
		//同步删除缓存数据
		Jedis jedis = jedisPool.getResource();
		for (Long id : ids) {
			jedis.del("brand"+id);
		}
		jedis.close();
	}
	@Override
	public Brand selectById(Long id) {
		return brandDao.getBrandById(id);
	}
	@Override
	public void editSave(Brand brand) {
		brandDao.updateById(brand);
		Jedis jedis = jedisPool.getResource();
		jedis.hset("brand"+brand.getId(),"id",brand.getId().toString());
		jedis.hset("brand"+brand.getId(),"name",brand.getName());
		jedis.close();
	}
	@Override
	public List<Brand> selectBrandByBrandExample(BrandExample brandExample) {
		return brandDao.selectBrandByExample(brandExample);
	}

}
