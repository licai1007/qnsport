package com.qingniao.core.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 提供本地和远程的Session的实现
 * @author licai
 *
 */
public interface SessionProvider {
	public void setSession(HttpServletRequest request,HttpServletResponse response,String name,String value);
	public String getSession(HttpServletRequest request,HttpServletResponse response,String name);
	public String getSessionId(HttpServletRequest request,HttpServletResponse response);
}
