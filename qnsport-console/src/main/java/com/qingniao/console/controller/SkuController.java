package com.qingniao.console.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qingniao.core.pojo.product.Sku;
import com.qingniao.core.pojo.product.SkuExample;
import com.qingniao.core.services.SkuBiz;

@Controller
@RequestMapping("/sku")
public class SkuController {
	@Autowired
	private SkuBiz skuBiz;
	@RequestMapping("/list.do")
	public String list(Long productId,Model model){
		SkuExample skuExample = new SkuExample();
		skuExample.createCriteria().andProductIdEqualTo(productId);
		List<Sku> skus = skuBiz.selectSkuByProductId(skuExample);
		model.addAttribute("skus", skus);
		return "sku/list";
	}
	@RequestMapping("/update.do")
	public void update(HttpServletResponse response,Sku sku) throws IOException{
		response.setContentType("application/json;charset=utf-8");
		skuBiz.updateSkuById(sku);
		JSONObject jo = new JSONObject();
		jo.put("message", "保存成功！");
		response.getWriter().write(jo.toString());
	
	}
}
