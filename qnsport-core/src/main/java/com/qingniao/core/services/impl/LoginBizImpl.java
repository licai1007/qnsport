package com.qingniao.core.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qingniao.core.common.MD5Utils;
import com.qingniao.core.dao.user.UserMapper;
import com.qingniao.core.pojo.user.User;
import com.qingniao.core.pojo.user.UserExample;
import com.qingniao.core.services.LoginBiz;
@Service
@Transactional
public class LoginBizImpl implements LoginBiz {
	@Autowired
	private UserMapper userMapper;
	@Override
	public User getUserByUsernameAndPassword(String username, String password) {
		UserExample userExample = new UserExample();
		userExample.createCriteria().andUsernameEqualTo(username).andPasswordEqualTo(MD5Utils.md5(password));
		List<User> users = userMapper.selectByExample(userExample);
		if(users.toString().equals("[]")){
			return null;
		}
		return users.get(0);
	}

}
