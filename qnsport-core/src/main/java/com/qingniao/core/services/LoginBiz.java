package com.qingniao.core.services;

import com.qingniao.core.pojo.user.User;

public interface LoginBiz {
	public User getUserByUsernameAndPassword(String username,String password);
}
