package com.qingniao.core.dao;

import java.util.List;

import com.qingniao.core.pojo.Brand;
import com.qingniao.core.pojo.BrandExample;

public interface BrandDao {
	public void addBrand(Brand brand);
	public List<BrandExample> getBrandExample(BrandExample brandExample);
	public Integer count(BrandExample brandExample);
	public void batchDelete(Long[] ids);
	public Brand getBrandById(Long id);
	public void updateById(Brand brand);
	public List<Brand> selectBrandByExample(BrandExample brandExample);
}
