package com.qingniao.core.services;



import java.util.List;

import com.qingniao.common.page.PageInfo;
import com.qingniao.core.pojo.Brand;
import com.qingniao.core.pojo.BrandExample;

public interface BrandBiz {
	public void addBrand(Brand brand);
	public PageInfo getBrandExample(BrandExample brandExample);
	public void batchDelete(Long[] ids);
	public Brand selectById(Long id);
	public void editSave(Brand brand);
	public List<Brand> selectBrandByBrandExample(BrandExample brandExample);
}
