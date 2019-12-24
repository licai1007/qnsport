package com.qingniao.cart;

import java.io.IOException;
import java.io.StringWriter;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.junit.Test;

import com.qingniao.core.pojo.user.User;

public class TestCart {
	@Test
	public void demo() throws JsonGenerationException, JsonMappingException, IOException{
		//将对象转换成json
		ObjectMapper om = new ObjectMapper();
		om.setSerializationInclusion(Inclusion.NON_NULL);//忽略null值
		User user = new User();
		user.setUsername("张三");
		user.setPassword("123456");
		StringWriter sw = new StringWriter();
		om.writeValue(sw, user);
		System.out.println(sw.toString());
		
		//将json转换成对象
		User value = om.readValue(sw.toString(),User.class);
		System.out.println(value.toString());
				
	}
}
