package com.qingniao.core.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.qingniao.core.common.Constants;
import com.qingniao.core.common.LocalSession;

public class LoginInterceptor implements HandlerInterceptor {
	@Autowired
	private LocalSession localSession;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String username = localSession.getSession(request, response,Constants.USER_NAME);
		String url = request.getParameter("url");
		String requestURI = request.getRequestURI();
		if(requestURI.startsWith("/buy")){
			if(username != null){
				request.setAttribute("isLogin",username);
			}else{
				if(url != null){
					response.sendRedirect("/login.html?url="+url);
					return false;
				}else{
					response.sendRedirect("/login.html");
					return false;
				}
			}
		}else{
			if(username != null){
				request.setAttribute("isLogin",username);
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
