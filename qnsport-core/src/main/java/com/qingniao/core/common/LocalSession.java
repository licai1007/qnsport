package com.qingniao.core.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
@Component
public class LocalSession implements SessionProvider {

	@Override
	public void setSession(HttpServletRequest request, HttpServletResponse response, String name, String value) {
		request.getSession().setAttribute(name, value);
	}

	@Override
	public String getSession(HttpServletRequest request, HttpServletResponse response, String name) {
		HttpSession session = request.getSession(false);
		if(session != null){
			return (String)session.getAttribute(name);
		}else{
			return null;
		}
		
	}

	@Override
	public String getSessionId(HttpServletRequest request, HttpServletResponse response) {
		return request.getSession().getId();
	}

}
