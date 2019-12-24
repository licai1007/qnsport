package com.qingniao.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.qingniao.core.pojo.user.User;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMarkerTest {
	@Test
	public void demo1() throws IOException, TemplateException{
		//1.创建一个Configuration对象
		Configuration configuration = new Configuration();
		//2.设置模板文件所在的路径
		String path = "F:/qnds/qingniaosport/parent/qnsport-console/src/main/webapp/WEB-INF/template";
		//3.加载模版目录和设置模板编码方式
		configuration.setDirectoryForTemplateLoading(new File(path));
		configuration.setDefaultEncoding("utf-8");
		//4.返回指定模版对象
		Template template = configuration.getTemplate("temp.html");
		//5.定义一个map用来存放数据
		Map<String,String> map = new HashMap<String,String>();
		map.put("name","hello freemarker");
		//6.输出文件的目录和名称
		Writer out = new FileWriter("C:/template/demo1.html");
		//7.通过模版输出数据
		template.process(map, out);
		//8.关闭流
		out.close();
		/**
		 * 取值
		 * ${name}
		 */
	}
	//输出对象
	@Test
	public void demo2() throws IOException, TemplateException{
		Configuration configuration = new Configuration();
		configuration.setDirectoryForTemplateLoading(new File("F:/qnds/qingniaosport/parent/qnsport-console/src/main/webapp/WEB-INF/template"));
		configuration.setDefaultEncoding("utf-8");
		Template template = configuration.getTemplate("temp.html");
		User user = new User();
		user.setUsername("张三");
		user.setPassword("123");
		Map<String,User> map = new HashMap<String,User>();
		map.put("user",user);
		Writer out = new FileWriter("C:/template/demo2.html");
		template.process(map, out);
		out.close();
		/**
		 * 页面取值
		 * 姓名：${user.username}
		 * 密码：${user.password}
		 */
	}
	//输出list
	@Test
	public void demo3() throws IOException, TemplateException{
		Configuration configuration = new Configuration();
		configuration.setDirectoryForTemplateLoading(new File("F:/qnds/qingniaosport/parent/qnsport-console/src/main/webapp/WEB-INF/template"));
		configuration.setDefaultEncoding("utf-8");
		Template template = configuration.getTemplate("temp.html");
		List<User> users = new ArrayList<User>();
		User user = new User();
		user.setUsername("张三");
		user.setPassword("123");
		users.add(user);
		
		User person = new User();
		person.setUsername("李四");
		person.setPassword("456");
		users.add(person);
		
		Map<String,List<User>> map = new HashMap<String,List<User>>();
		map.put("users",users);
		
		Writer out = new FileWriter("C:/template/demo3.html");
		template.process(map, out);
		out.close();
		/**
		 * 取值
		 * <#list users as user>
			${user.username}
			${user.password}
			</#list>
		 */
	}
	//输出map
	@Test
	public void demo4() throws IOException, TemplateException{
		Configuration configuration = new Configuration();
		configuration.setDirectoryForTemplateLoading(new File("F:/qnds/qingniaosport/parent/qnsport-console/src/main/webapp/WEB-INF/template"));
		configuration.setDefaultEncoding("utf-8");
		Template template = configuration.getTemplate("temp.html");
		Map<String,String> map = new HashMap<String,String>();
		map.put("a","25");
		map.put("b","26");
		map.put("c","27");
		
		Map<String,Map<String,String>> root = new HashMap<String,Map<String,String>>();
		root.put("map",map);
		
		Writer out = new FileWriter("C:/template/demo4.html");
		template.process(root, out);
		out.close();
		/**
		 * 取值
		 * <#list map?keys as key>
			${map[key]}
			</#list>
		 */
	}
	//输出list中的map
	@Test
	public void demo5() throws IOException, TemplateException{
		Configuration configuration = new Configuration();
		configuration.setDirectoryForTemplateLoading(new File("F:/qnds/qingniaosport/parent/qnsport-console/src/main/webapp/WEB-INF/template"));
		configuration.setDefaultEncoding("utf-8");
		Template template = configuration.getTemplate("temp.html");
		List<Map<String,String>> maps = new ArrayList<Map<String,String>>();
		Map<String,String> map = new HashMap<String,String>();
		map.put("name","张三");
		map.put("age","25");
		maps.add(map);
		Map<String,List<Map<String,String>>> root = new HashMap<String, List<Map<String,String>>>();
		root.put("maps",maps);
		Writer out = new FileWriter("C:/template/demo5.html");
		template.process(root, out);
		out.close();
		/**
		 * <#list maps as map>
			${map.name}
			${map.age}
			</#list>
		 */
	}
	//获取下标
	@Test
	public void demo6() throws IOException, TemplateException{
		Configuration configuration = new Configuration();
		configuration.setDirectoryForTemplateLoading(new File("F:/qnds/qingniaosport/parent/qnsport-console/src/main/webapp/WEB-INF/template"));
		configuration.setDefaultEncoding("utf-8");
		Template template = configuration.getTemplate("temp.html");
		List<User> users = new ArrayList<User>();
		User user1 = new User();
		user1.setUsername("张三");
		users.add(user1);
		User user2 = new User();
		user2.setUsername("李四");
		users.add(user2);
		Map<String,List<User>> root = new HashMap<String,List<User>>();
		root.put("users",users);
		Writer out = new FileWriter("C:/template/demo6.html");
		template.process(root, out);
		out.close();
		/**
		 * <#list users as u>
			${u_index}
			</#list>
		 */
	}
	//在模板中赋值
	@Test
	public void demo7() throws IOException, TemplateException{
		Configuration configuration = new Configuration();
		configuration.setDirectoryForTemplateLoading(new File("F:/qnds/qingniaosport/parent/qnsport-console/src/main/webapp/WEB-INF/template"));
		configuration.setDefaultEncoding("utf-8");
		Template template = configuration.getTemplate("temp.html");
		Map<String,String> root = new HashMap<String,String>();
		root.put("name","在模板中赋值");
		Writer out = new FileWriter("C:/template/demo7.html");
		template.process(root, out);
		out.close();
		/**
		 * 1:<#assign a=0/>
			${a}
			2:<#assign b="${name}"/>
			${b}
			3:<#assign c>freemarker经常使用</#assign>
			${c}
		 */
	}
	//逻辑判断
	@Test
	public void demo8() throws IOException, TemplateException{
		Configuration configuration = new Configuration();
		configuration.setDirectoryForTemplateLoading(new File("F:/qnds/qingniaosport/parent/qnsport-console/src/main/webapp/WEB-INF/template"));
		configuration.setDefaultEncoding("utf-8");
		Template template = configuration.getTemplate("temp1.html");
		Map<String,String> root = new HashMap<String,String>();
		Writer out = new FileWriter("C:/template/demo8.html");
		template.process(root, out);
		out.close();

	}
	//日期处理
	@Test
	public void demo9() throws IOException, TemplateException{
		Configuration configuration = new Configuration();
		configuration.setDirectoryForTemplateLoading(new File("F:/qnds/qingniaosport/parent/qnsport-console/src/main/webapp/WEB-INF/template"));
		configuration.setDefaultEncoding("utf-8");
		Template template = configuration.getTemplate("temp.html");
		Map<String,Date> root = new HashMap<String,Date>();
		root.put("cur_time",new Date());
		Writer out = new FileWriter("C:/template/demo9.html");
		template.process(root, out);
		out.close();
		/**
		 * 1.日期
		${cur_time?date}
		<br/>
		2.日期和时间
		${cur_time?datetime}
		<br/>
		3.时间
		${cur_time?time}
		 */
	}
	//null值的处理
	@Test
	public void demo10() throws IOException, TemplateException{
		Configuration configuration = new Configuration();
		configuration.setDirectoryForTemplateLoading(new File("F:/qnds/qingniaosport/parent/qnsport-console/src/main/webapp/WEB-INF/template"));
		configuration.setDefaultEncoding("utf-8");
		Template template = configuration.getTemplate("temp.html");
		Map<String,Date> root = new HashMap<String,Date>();
		root.put("name",null);
		Writer out = new FileWriter("C:/template/demo10.html");
		template.process(root, out);
		out.close();
		/**
		 * ${name!}
		 * ${name!"我是null"}
		 */
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
