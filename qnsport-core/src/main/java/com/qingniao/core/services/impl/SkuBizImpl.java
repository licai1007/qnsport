package com.qingniao.core.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qingniao.core.dao.product.ColorMapper;
import com.qingniao.core.dao.product.ImgMapper;
import com.qingniao.core.dao.product.ProductMapper;
import com.qingniao.core.dao.product.SkuMapper;
import com.qingniao.core.pojo.product.Color;
import com.qingniao.core.pojo.product.ColorExample;
import com.qingniao.core.pojo.product.Img;
import com.qingniao.core.pojo.product.ImgExample;
import com.qingniao.core.pojo.product.Product;
import com.qingniao.core.pojo.product.Sku;
import com.qingniao.core.pojo.product.SkuExample;
import com.qingniao.core.services.SkuBiz;
@Service
@Transactional
public class SkuBizImpl implements SkuBiz {
	@Autowired
	private ImgMapper imgMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ColorMapper colorMapper;
	@Autowired
	private SkuMapper skuMapper;
	@Override
	public int insertSelective(Sku record) {
		return skuMapper.insertSelective(record);
	}
	@Override
	public List<Sku> selectSkuByProductId(SkuExample skuExample) {
		List<Sku> skus = skuMapper.selectByExample(skuExample);
		for (Sku sku : skus) {
			ColorExample colorExample = new ColorExample();
			colorExample.createCriteria().andIdEqualTo(sku.getColorId());
			List<Color> colors = colorMapper.selectByExample(colorExample);
			sku.setColor(colors.get(0));
		}
		return skus;
	}
	@Override
	public void updateSkuById(Sku sku) {
		skuMapper.updateByPrimaryKeySelective(sku);
	}
	@Override
	public Sku loadSkuById(Long id) {
		Sku sku = skuMapper.selectByPrimaryKey(id);
		//关联颜色
		sku.setColor(colorMapper.selectByPrimaryKey(sku.getColorId()));
		
		Product product = productMapper.selectByPrimaryKey(sku.getProductId());
		
		//关联图片
		ImgExample imgExample = new ImgExample();
		imgExample.createCriteria().andProductIdEqualTo(product.getId()).andIsDefEqualTo(false);
		List<Img> imgs = imgMapper.selectByExample(imgExample);
		product.setImg(imgs.get(0));
		
		//关联商品
		sku.setProduct(product);
		return sku;
	}
}
