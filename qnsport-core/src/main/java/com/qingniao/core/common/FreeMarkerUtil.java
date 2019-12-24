package com.qingniao.core.common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
/**
 * 生成静态页工具类
 * @author licai
 *
 */
@Service
public class FreeMarkerUtil implements ServletContextAware{
	@Autowired
	private FreeMarkerConfig freeMarkerConfig;
	private ServletContext servletContext;
	public void toHTML(Map map){
		Long id = (Long) map.get("productId");//商品id作为静态页名
		Configuration configuration = freeMarkerConfig.getConfiguration();
		try {
			Template template = configuration.getTemplate("productDetail.html");
			String path = "/html/product/" + id +".html";
			//获取服务器绝对路经
			String realPath = servletContext.getRealPath("");
			String filePath = realPath + path;//绝对路经
			Writer out = new OutputStreamWriter(new FileOutputStream(filePath),"utf-8");
			template.process(map, out);
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}
