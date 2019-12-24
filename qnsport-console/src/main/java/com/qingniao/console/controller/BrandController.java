package com.qingniao.console.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qingniao.common.page.PageInfo;
import com.qingniao.core.pojo.Brand;
import com.qingniao.core.pojo.BrandExample;
import com.qingniao.core.services.BrandBiz;

@Controller
@RequestMapping("/brand")
public class BrandController {
	@Autowired
	private BrandBiz brandBiz;
	@RequestMapping("/list.do")
	public String brandList(Model model,BrandExample brandExample,HttpServletRequest request){
		Integer status = brandExample.getStatus();
		String name = brandExample.getName();
		Integer pageNo = brandExample.getPageNo();
		StringBuilder stringBuilder = new StringBuilder();
		if(pageNo != null){
			brandExample.setPageNo(pageNo);
		}else{
			brandExample.setPageNo(1);
		}
		if(status == null){
			brandExample.setStatus(1);
		}else{
			brandExample.setStatus(status);
			stringBuilder.append("status="+status);
		}
		if(name != "" & name != null){
			brandExample.setName(name.trim());
			model.addAttribute("name",name.trim());
			stringBuilder.append("&name="+name.trim());
		}
		
		PageInfo pageInfo = brandBiz.getBrandExample(brandExample);
		String url = "/brand/list.do";
		pageInfo.pageView(url, stringBuilder.toString());
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("pageNo",brandExample.getPageNo());
		model.addAttribute("status", brandExample.getStatus());
		return "/brand/list";
	}
	@RequestMapping("/add.do")
	public String add(){
		return "/brand/add";
	}
	@RequestMapping("/baocun.do")
	public String baocun(Brand brand){
		brandBiz.addBrand(brand);
		return "redirect:list.do";
	}
	@RequestMapping("/batchDelete.do")
	public String batchDelete(Model model,BrandExample brandExample){
		Integer status = brandExample.getStatus();
		String name = brandExample.getName();
		Integer pageNo = brandExample.getPageNo();
		model.addAttribute("status", status);
		if(name.length()>0){
			model.addAttribute("name", name);
		}
		model.addAttribute("pageNo", pageNo);
		if(brandExample.getId() != null){
			Long[] id = new Long[]{brandExample.getId()};
			brandBiz.batchDelete(id);
		}else{
			brandBiz.batchDelete(brandExample.getIds());
		}
		return "forward:list.do";
	}
	@RequestMapping("/editBrand.do")
	public String editBrand(Model model,Long id){
		Brand brands = brandBiz.selectById(id);
		model.addAttribute("brands", brands);
		return "/brand/edit";
	}
	@RequestMapping("/edit.do")
	public String edit(Brand brand){
		brandBiz.editSave(brand);
		return "redirect:list.do";
	}

}
