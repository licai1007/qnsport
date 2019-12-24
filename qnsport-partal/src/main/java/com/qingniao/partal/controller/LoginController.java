package com.qingniao.partal.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qingniao.core.common.Constants;
import com.qingniao.core.common.LocalSession;
import com.qingniao.core.pojo.user.User;
import com.qingniao.core.services.LoginBiz;

@Controller
public class LoginController {
	@Autowired
	private LocalSession localSession;
	@Autowired
	private LoginBiz loginBiz;
	//get请求
	@RequestMapping(value="/login.html",method = RequestMethod.GET)
	public String login(Model model,String url){
		model.addAttribute("url",url);
		return "buyer/login";
	}
	//post请求
	@RequestMapping(value="/login.html",method = RequestMethod.POST)
	public String login(HttpServletRequest request,HttpServletResponse response,Model model,String username,String password,String code,String url){
		if(code != ""){
			String scode = localSession.getSession(request, response,Constants.USER_CODE);
			if(code.toLowerCase().equals(scode.toLowerCase())){
				if(username != null && username.trim().length() > 0 && password != null && password.trim().length() > 0){
					User user = loginBiz.getUserByUsernameAndPassword(username, password);
					if(user != null){
						localSession.setSession(request, response,Constants.USER_NAME,user.getUsername());
						return "redirect:"+url;
					}else{
						model.addAttribute("url",url);
						model.addAttribute("error","用户名或密码输入错误！");
					}
				}else{
					model.addAttribute("url",url);
					model.addAttribute("error","用户名或密码没有输入！");
				}
			}else{
				model.addAttribute("url",url);
				model.addAttribute("error","验证码输入错误！");
			}
			
		}else{
			model.addAttribute("url",url);
			model.addAttribute("error","验证码不能为空！");
		}
		return "buyer/login";
	}
	//验证码
	@RequestMapping("/login/getCode.html")
	public void getCode(HttpServletRequest request,HttpServletResponse response){
		BufferedImage image = new BufferedImage(68,22,BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		Color c = new Color(200, 150, 255);
		g.setColor(c);
		g.fillRect(0, 0, 68,22);
		Random r = new Random();
		StringBuffer sb = new StringBuffer();
		char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		int index,len = chars.length;
		for(int i = 0;i < 4;i++){
			g.setColor(new Color(r.nextInt(88),r.nextInt(188),r.nextInt(255)));
			g.setFont(new Font("Arial",Font.BOLD | Font.ITALIC,22));
			index = r.nextInt(len);
			g.drawString(""+chars[index],(i*15)+3,18);
			sb.append(chars[index]);
		}
		localSession.setSession(request, response,Constants.USER_CODE,sb.toString());
		try {
			ImageIO.write(image,"jpg",response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
