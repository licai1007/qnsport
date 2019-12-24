package com.qingniao.core.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
/**
 * 远程session的实现
 * @author licai
 *
 */
@Component
public class RemoteStorageSession implements SessionProvider {
	@Autowired
	private JedisPool jedisPool;
	//判断是用户名还是验证码
	@Override
	public void setSession(HttpServletRequest request, HttpServletResponse response, String name, String value) {
		Jedis jedis = jedisPool.getResource();
		if(name.equals(Constants.USER_NAME)){
			jedis.set(name+getSessionId(request, response), value);
			jedis.expire(name+getSessionId(request, response),60*30);//缓存30分钟
		}else{
			jedis.set(name+getSessionId(request, response), value);
			jedis.expire(name+getSessionId(request, response),30);//缓存30秒
		}
		jedis.close();
	}

	@Override
	public String getSession(HttpServletRequest request, HttpServletResponse response, String name) {
		Jedis jedis = jedisPool.getResource();
		String value = jedis.get(name+getSessionId(request, response));
		jedis.close();
		return value;
	}

	@Override
	public String getSessionId(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		String c = findByCookie(cookies,Constants.USER_SESSIONID);
		if(c != null){
			return c;
		}else{
		//第一次
		String sessionId = IDUtils.getSessoinId();
		Cookie cookie = new Cookie(Constants.USER_SESSIONID,sessionId);
		cookie.setPath("/");
		response.addCookie(cookie);
		return sessionId;
		}
	}
	public String findByCookie(Cookie[] cookies,String name){
		for (Cookie cookie : cookies) {
			if(cookie.equals(name)){
				return cookie.getValue();
			}
		}
		return null;
	}

}
