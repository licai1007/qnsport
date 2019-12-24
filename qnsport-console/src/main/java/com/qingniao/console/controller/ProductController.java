package com.qingniao.console.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qingniao.common.page.PageInfo;
import com.qingniao.core.pojo.Brand;
import com.qingniao.core.pojo.BrandExample;
import com.qingniao.core.pojo.product.Color;
import com.qingniao.core.pojo.product.ColorExample;
import com.qingniao.core.pojo.product.Feature;
import com.qingniao.core.pojo.product.FeatureExample;
import com.qingniao.core.pojo.product.Img;
import com.qingniao.core.pojo.product.Product;
import com.qingniao.core.pojo.product.ProductExample;
import com.qingniao.core.pojo.product.ProductExample.Criteria;
import com.qingniao.core.pojo.product.Sku;
import com.qingniao.core.pojo.product.SkuExample;
import com.qingniao.core.pojo.product.Type;
import com.qingniao.core.pojo.product.TypeExample;
import com.qingniao.core.services.BrandBiz;
import com.qingniao.core.services.ColorBiz;
import com.qingniao.core.services.FeatureBiz;
import com.qingniao.core.services.ImgBiz;
import com.qingniao.core.services.ProductBiz;
import com.qingniao.core.services.SkuBiz;
import com.qingniao.core.services.TypeBiz;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Controller
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private TypeBiz typeBiz;
	@Autowired
	private BrandBiz brandBiz;
	@Autowired
	private FeatureBiz featureBiz;
	@Autowired
	private ColorBiz colorBiz;
	@Autowired
	private ProductBiz productBiz;
	@Autowired
	private ImgBiz imgBiz;
	@Autowired
	private SkuBiz skuBiz;
	@Autowired
	private JedisPool jedisPool;
	@RequestMapping("/add.do")
	public String add(Model model){
		//商品类型
		TypeExample typeExample = new TypeExample();
		typeExample.createCriteria().andParentIdNotEqualTo(0l);
		List<Type> types = typeBiz.selectTypeByTypeExample(typeExample);
		model.addAttribute("types", types);
		//商品品牌
		BrandExample brandExample = new BrandExample();
		brandExample.setStatus(1);
		List<Brand> brands = brandBiz.selectBrandByBrandExample(brandExample);
		model.addAttribute("brands", brands);
		//材质
		FeatureExample featureExample = new FeatureExample();
		featureExample.createCriteria().andIsDelEqualTo(true);
		List<Feature> features = featureBiz.selectFeatureByFeatureExample(featureExample);
		model.addAttribute("features", features);
		//颜色
		ColorExample colorExample = new ColorExample();
		colorExample.createCriteria().andParentIdNotEqualTo(0l);
		List<Color> colors = colorBiz.selectColorByColorExample(colorExample);
		model.addAttribute("colors", colors);
		return "/product/add";
	}
	@RequestMapping("/save.do")
	public String save(Product product){
		//保存商品
		Jedis jedis = jedisPool.getResource();
		Long pid = jedis.incr("productId");
		product.setId(pid);
		product.setWeight(product.getWeight()*1000);
		product.setCreateTime(new Date());//生成时间
		product.setIsDel(false);//没被删除
		product.setIsShow(false);//默认下架
		productBiz.insertSelective(product);
		//保存图片
		Img img = product.getImg();
		img.setProductId(pid);
		img.setIsDef(false);
		imgBiz.insertSelective(img);
		//保存sku(最小销售单元)
		String colors = product.getColors();
		String sizes = product.getSizes();
		for (String color : colors.split(",")) {
			//创建sku
			Sku sku = new Sku();
			sku.setColorId(Long.parseLong(color));
			sku.setProductId(pid);
			sku.setCreateTime(new Date());
			for(String size:sizes.split(",")){
				//设置尺寸
				sku.setSize(size);
				//设置运费
				sku.setDeliveFee(10f);
				//设置市场价
				sku.setMarketPrice(120f);
				//设置售价
				sku.setPrice(135f);
				//设置库存
				sku.setStock(1000);
				//设置购买限制
				sku.setUpperLimit(23);
				skuBiz.insertSelective(sku);
				
			}
		}
		return "redirect:/product/list.do";
	}
	@RequestMapping("/list.do")
	public String list(Model model,String name,Long brandId,Boolean isShow,Integer pageNo){
		//初始化数据
		BrandExample brandExample = new BrandExample();
		brandExample.setStatus(1);
		List<Brand> brands = brandBiz.selectBrandByBrandExample(brandExample);
		model.addAttribute("brands", brands);
		
		ProductExample example = new ProductExample();
		Criteria create = example.createCriteria();
		create.andIsDelEqualTo(true);
		StringBuilder stringBuilder = new StringBuilder();
		
		if(null != name && name.trim().length() != 0){
			create.andNameLike("%"+name+"%");
			stringBuilder.append("name="+name.trim());
			model.addAttribute("name", name.trim());
		}
		if(null != brandId){
			create.andBrandIdEqualTo(brandId);
			stringBuilder.append("&brandId="+brandId);
			model.addAttribute("brandId", brandId);
		}
		if(null == isShow){
			isShow = false;
		}
		stringBuilder.append("&isShow="+isShow);
		create.andIsShowEqualTo(isShow);
		model.addAttribute("isShow",isShow);
		if(null != pageNo){
			example.setPageNo(pageNo);
			model.addAttribute("pageNo",pageNo);
		}else{
			example.setPageNo(1);
			model.addAttribute("pageNo",1);
		}
		String url = "/product/list.do";
		PageInfo pageInfo = productBiz.selectByExample(example);
		pageInfo.pageView(url, stringBuilder.toString());
		model.addAttribute("pageInfo", pageInfo);
		return "product/list";
	}
	@RequestMapping("/onSale.do")
	public String onSale(Long[] ids){
		productBiz.onSave(ids);
		return "redirect:list.do";
	}
	@RequestMapping("/sellOut.do")
	public String sellOut(Long[] ids){
		productBiz.sellOut(ids);
		return "redirect:list.do";
	}
	@RequestMapping("/look.do")
	public String look(Long productId,Model model){
		Product product = productBiz.selectProductAndImgByProductId(productId);
		model.addAttribute("product",product);
		//加载sku
		SkuExample skuExample = new SkuExample();
		skuExample.createCriteria().andProductIdEqualTo(productId);
		List<Sku> skus = skuBiz.selectSkuByProductId(skuExample);
		model.addAttribute("skus",skus);
		//颜色去重
		Set<Color> colors = new HashSet<Color>();
		for (Sku sku : skus) {
			colors.add(sku.getColor());
		}
		model.addAttribute("colors",colors);
		//销量
		int sum = 0;
		for (Sku sku : skus) {
			sum = (int) (sum + sku.getSales());
		}
		model.addAttribute("sum",sum);
		//商品类型
		TypeExample typeExample = new TypeExample();
		typeExample.createCriteria().andIdEqualTo(product.getTypeId());
		List<Type> types = typeBiz.selectTypeByTypeExample(typeExample);
		model.addAttribute("type",types.get(0));
		//品牌
		Brand brand = brandBiz.selectById(product.getBrandId());
		model.addAttribute("brand",brand);
		//属性
		List<String> features = new ArrayList<String>();
		for(int i=0;i<product.getFeatures().split(",").length;i++){
			FeatureExample featureExample = new FeatureExample();
			featureExample.createCriteria().andIdEqualTo(new Long(product.getFeatures().split(",")[i]));
			List<Feature> feature = featureBiz.selectFeatureByFeatureExample(featureExample);
			features.add(feature.get(0).getName());
		}
		model.addAttribute("features",features);
		return "product/view";
	}
	
	@RequestMapping("/optDelete.do")
	public String optDelete(Model model,Product product){
		Integer pageNo = product.getPageNo();
		String name = product.getName();
		Long brandId = product.getBrandId();
		Boolean isShow = product.getIsShow();
		model.addAttribute("pageNo",pageNo);
		if(name != ""){
			model.addAttribute("name",name);
		}
		if(brandId != null){
			model.addAttribute("brandId",brandId);
		}
		model.addAttribute("isShow",isShow);
		if(product.getIds() != null){
			for(Long id:product.getIds()){
				productBiz.optDelete(id,isShow);
			}
		}else{
			productBiz.optDelete(product.getId(),isShow);
		}
		return "forward:list.do";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
