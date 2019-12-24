package com.qingniao.partal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qingniao.common.page.PageInfo;
import com.qingniao.core.pojo.Brand;
import com.qingniao.core.pojo.product.Color;
import com.qingniao.core.pojo.product.Img;
import com.qingniao.core.pojo.product.Product;
import com.qingniao.core.pojo.product.ProductExample;
import com.qingniao.core.pojo.product.Sku;
import com.qingniao.core.pojo.product.SkuExample;
import com.qingniao.core.services.ProductBiz;
import com.qingniao.core.services.SkuBiz;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
@Controller
public class ProductController {
	@Autowired
	private HttpSolrServer server;
	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private ProductBiz productBiz;
	@Autowired
	private SkuBiz skuBiz;
	@RequestMapping("/index.html")
	public String index(Model model,String pname,Integer pageNo,Long brandId,String price) throws SolrServerException{
		//从缓存中取出品牌数据
		Jedis jedis = jedisPool.getResource();
		List<Brand> brands = new ArrayList<Brand>();
		Set<String> keys = jedis.keys("brand*");
		for (String key : keys) {
			Brand brand = new Brand();
			brand.setId(Long.parseLong(jedis.hget(key,"id")));
			brand.setName(jedis.hget(key,"name"));
			brands.add(brand);
		}
		model.addAttribute("brands",brands);
		
		ProductExample productExample = new ProductExample();
		productExample.setPageSize(3);
		SolrQuery solrQuery = new SolrQuery();
		
		//定义标签控制已选条件的显示
		Boolean flag = false;
		//用map集合来存放已选条件
		Map<String,String> condition = new HashMap<String,String>();
		
		StringBuilder stringBuilder = new StringBuilder();
		if(null != pageNo){
			productExample.setPageNo(pageNo);
			model.addAttribute("pageNo",pageNo);
		}else{
			productExample.setPageNo(1);
		}
		if(pname != null && pname.trim().length()>0){
			solrQuery.set("q","name_ik:"+pname);
			//高亮显示搜索关键词
			//开启高亮
			solrQuery.setHighlight(true);
			//设置高亮字段
			solrQuery.addHighlightField("name_ik");
			//设置高亮前缀
			solrQuery.setHighlightSimplePre("<span style='color:red;'>");
			//设置高亮后缀
			solrQuery.setHighlightSimplePost("</span>");
			model.addAttribute("pname",pname.trim());
			stringBuilder.append("pname="+pname.trim());
		}else{
			solrQuery.set("q","*:*");
		}
		//判断品牌
		if(null != brandId){
			solrQuery.addFilterQuery("brandId:"+brandId);
			stringBuilder.append("&brandId="+brandId);
			model.addAttribute("brandId", brandId);
			condition.put("品牌",jedis.hget("brand"+brandId,"name"));
			flag = true;
		}
		//判断价格
		if(null != price){
			String[] p = price.split("-");
			if(p.length==2){
				Float start = new Float(p[0]);
				Float end = new Float(p[1]);
				solrQuery.addFilterQuery("price:["+start+" TO "+end+"]");
				condition.put("价格",price);
			}else{
				Float start = new Float(600l);
				Float end = new Float(10000000000000000l);
				solrQuery.addFilterQuery("price:["+start+" TO "+end+"]");
				condition.put("价格","600以上");
			}
			stringBuilder.append("&price="+price);
			model.addAttribute("price", price);
			flag = true;
		}
		
		model.addAttribute("flag",flag);
		model.addAttribute("condition",condition);
		
		solrQuery.setStart(productExample.getStartRow());
		solrQuery.setRows(productExample.getPageSize());
		solrQuery.addSort("price",ORDER.asc);
		
		QueryResponse query = server.query(solrQuery);
		SolrDocumentList docs = query.getResults();
		List<Product> products = new ArrayList<Product>();
		for (SolrDocument doc : docs) {
			Product product = new Product();
			//id
			String id = (String) doc.get("id");
			product.setId(Long.parseLong(id));
			//name
			if(pname != null && pname.trim().length()>0){
				Map<String, Map<String, List<String>>> highlighting = query.getHighlighting();
				Map<String, List<String>> map = highlighting.get(id);
				List<String> list = map.get("name_ik");
				String word = list.get(0);
				product.setName(word);
			}else{
				String name = (String) doc.get("name_ik");
				product.setName(name);
			}
			//url
			Img img = new Img();
			String url = (String) doc.get("url");
			img.setUrl(url);
			product.setImg(img);
			//price
			float p = (float) doc.get("price");
			product.setPrice(p);
			//brandId
			Integer bId = (Integer) doc.get("brandId");
			product.setBrandId(Long.parseLong(bId.toString()));
			products.add(product);
		}
		String url = "/index.html";
		PageInfo pageInfo = new PageInfo(productExample.getPageNo(),productExample.getPageSize(),(int)docs.getNumFound());
		pageInfo.setList(products);
		pageInfo.pageView(url,stringBuilder.toString());
		model.addAttribute("pageInfo", pageInfo);
		return "product/product";
	}
	//商品详情页控制器
	@RequestMapping("/product/detail.html")
	public String detail(Long id,Model model){
		//加载商品数据及图片
		Product product = productBiz.selectProductAndImgByProductId(id);
		//加载sku
		SkuExample skuExample = new SkuExample();
		skuExample.createCriteria().andProductIdEqualTo(id);
		List<Sku> skus = skuBiz.selectSkuByProductId(skuExample);
		//颜色
		Set<Color> colors = new HashSet<Color>();
		for (Sku sku : skus) {
			colors.add(sku.getColor());
		}
		model.addAttribute("product", product);
		model.addAttribute("skus", skus);
		model.addAttribute("colors",colors);
		return "product/productDetail";
	}
}
