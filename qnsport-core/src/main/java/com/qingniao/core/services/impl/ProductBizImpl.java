package com.qingniao.core.services.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qingniao.common.page.PageInfo;
import com.qingniao.core.common.FreeMarkerUtil;
import com.qingniao.core.dao.product.ImgMapper;
import com.qingniao.core.dao.product.ProductMapper;
import com.qingniao.core.dao.product.SkuMapper;
import com.qingniao.core.pojo.product.Color;
import com.qingniao.core.pojo.product.Img;
import com.qingniao.core.pojo.product.ImgExample;
import com.qingniao.core.pojo.product.Product;
import com.qingniao.core.pojo.product.ProductExample;
import com.qingniao.core.pojo.product.Sku;
import com.qingniao.core.pojo.product.SkuExample;
import com.qingniao.core.services.ProductBiz;
import com.qingniao.core.services.SkuBiz;
@Service
@Transactional
public class ProductBizImpl implements ProductBiz {
	@Autowired
	private FreeMarkerUtil freeMarkerUtil;
	@Autowired
	private SkuBiz skuBiz;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ImgMapper imgMapper;
	@Autowired
	private HttpSolrServer server;
	@Autowired
	private SkuMapper skuMapper;
	@Override
	public int insertSelective(Product record) {
		record.setIsDel(true);//默认没删除
		return productMapper.insertSelective(record);
	}
	@Override
	public PageInfo selectByExample(ProductExample example) {
		example.setPageSize(3);
		PageInfo pageInfo = new PageInfo(example.getPageNo(),example.getPageSize(),productMapper.countByExample(example));
		example.setPageNo(pageInfo.getPageNo());
		List<Product> products = productMapper.selectByExample(example);
		for (Product product : products) {
			ImgExample imgExample =new ImgExample();
			imgExample.createCriteria().andProductIdEqualTo(product.getId()).andIsDefEqualTo(false);
			List<Img> imgs = imgMapper.selectByExample(imgExample);
			product.setImg(imgs.get(0));
		}
		pageInfo.setList(products);
		return pageInfo;
	}
	@Override
	public int countByExample(ProductExample example) {
		return productMapper.countByExample(example);
	}
	@Override
	public void onSave(Long[] ids) {
		for (Long id : ids) {
			Product product = new Product();
			product.setId(id);
			product.setIsShow(true);
			productMapper.updateByPrimaryKeySelective(product);
			
			Product p = productMapper.selectByPrimaryKey(id);
			//id
			SolrInputDocument sid = new SolrInputDocument();
			sid.setField("id",id);
			//name
			sid.setField("name_ik",p.getName());
			//url
			ImgExample imgExample = new ImgExample();
			imgExample.createCriteria().andProductIdEqualTo(id).andIsDefEqualTo(false);
			List<Img> imgs = imgMapper.selectByExample(imgExample);
			sid.setField("url",imgs.get(0).getUrl());
			//price
			float price = skuMapper.selectMinPriceByProductId(id);
			sid.setField("price",price);
			//brandId
			sid.setField("brandId",p.getBrandId());
			try {
				server.add(sid);
				server.commit();
			} catch (SolrServerException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//生成静态页面
			//生成静态页面所需要的数据
			Map<String,Object> root = new HashMap<String,Object>();
			root.put("productId",id);
			//加载商品数据及图片
			Product products = selectProductAndImgByProductId(id);
			//加载sku
			SkuExample skuExample = new SkuExample();
			skuExample.createCriteria().andProductIdEqualTo(id);
			List<Sku> skus = skuBiz.selectSkuByProductId(skuExample);
			//颜色
			Set<Color> colors = new HashSet<Color>();
			for (Sku sku : skus) {
				colors.add(sku.getColor());
			}
			root.put("products", products);
			root.put("skus", skus);
			root.put("colors",colors);
			//调用工具类生成静态页面
			freeMarkerUtil.toHTML(root);
		}
	}
	@Override
	public Product selectProductAndImgByProductId(Long id) {
		Product product = productMapper.selectByPrimaryKey(id);
		ImgExample imgExample = new ImgExample();
		imgExample.createCriteria().andProductIdEqualTo(id).andIsDefEqualTo(false);
		List<Img> colors = imgMapper.selectByExample(imgExample);
		product.setImg(colors.get(0));
		return product;
	}
	@Override
	public void sellOut(Long[] ids) {
		for (Long id : ids) {
			Product product = new Product();
			product.setId(id);
			product.setIsShow(false);
			productMapper.updateByPrimaryKeySelective(product);
			try {
				server.deleteById(id.toString());
				server.commit();
			} catch (SolrServerException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	@Override
	public void optDelete(Long id,Boolean isShow) {
		Product product = new Product();
		product.setId(id);
		product.setIsDel(false);
		productMapper.updateByPrimaryKeySelective(product);
		if(isShow){
			try {
				server.deleteById(id.toString());
				server.commit();
			} catch (SolrServerException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
