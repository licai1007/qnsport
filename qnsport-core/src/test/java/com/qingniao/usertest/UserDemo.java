package com.qingniao.usertest;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qingniao.core.dao.UserTestMapper;
import com.qingniao.core.pojo.UserTest;
import com.qingniao.core.pojo.user.User;
import com.qingniao.core.services.LoginBiz;
import com.qingniao.core.services.UserTestBizImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-*.xml")
public class UserDemo {
	@Autowired
	private UserTestMapper userTestMapper;
	@Autowired
	private UserTestBizImpl userTestBizImpl;
	@Autowired
	private LoginBiz loginBiz;
	@Test
	public void demo1(){
		// 从容器中获得mapper代理对象
		UserTest userTest = new UserTest(1,"张三","123");
		int flag = userTestMapper.insertUserTest(userTest);
		String str = (flag == 1)?"插入成功":"插入失败";
		System.out.println(str);
	}
	@Test
	public void demo2(){
		UserTest userTest = new UserTest(4,"赵六","abc");
		int i = userTestBizImpl.insertUserTest(userTest);
		String str = (i == 1)?"插入成功":"插入失败";
		System.out.println(str);
	}
	@Test
	public void demo3(){
		User user = loginBiz.getUserByUsernameAndPassword("zhangsan","123456");
		System.out.println(user.toString());
	}
}
