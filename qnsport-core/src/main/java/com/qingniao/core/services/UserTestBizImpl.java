package com.qingniao.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qingniao.core.dao.UserTestMapper;
import com.qingniao.core.pojo.UserTest;

@Service
@Transactional
public class UserTestBizImpl {
	@Autowired
	private UserTestMapper userTestMapper;
	public int insertUserTest(UserTest user){
		int flag = userTestMapper.insertUserTest(user);
		int a = 23/0;
		return flag;
	}
}
