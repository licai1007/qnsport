package com.qingniao.redis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-*.xml")
public class TestJedis {
	//设置数据
	@Test
	public void demo1(){
		Jedis jedis = new Jedis("192.168.182.134",6379);
		jedis.set("name","lisi");
		jedis.close();
	}
	//取出数据
	@Test
	public void demo2(){
		Jedis jedis = new Jedis("192.168.182.134",6379);
		String name = jedis.get("name");
		System.out.println(name);
		jedis.close();
	}
	//使用连接池
	@Test
	public void demo3(){
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(100);
		JedisPool jedisPool = new JedisPool(jedisPoolConfig,"192.168.182.134",6379);
		Jedis jedis = jedisPool.getResource();
		jedis.hset("admin","name","张三");
		jedis.hset("admin","age","30");
		String name = jedis.hget("admin","name");
		String age = jedis.hget("admin","age");
		System.out.println(name+"---"+age);
		jedis.close();
	}
	//测试redis整合spring
	@Autowired
	private JedisPool jedispool;
	@Test
	public void demo4(){
		Jedis jedis = jedispool.getResource();
		jedis.set("uname","tongtong");
		jedis.close();
	}
	//测试集群版redis
	@Test
	public void demo5(){
		//创建连接池配置类
		JedisPoolConfig config = new JedisPoolConfig();
		//设置最大连接数
		config.setMaxTotal(50);
		
		//节点
		Set<HostAndPort> hosts = new HashSet<HostAndPort>();
		hosts.add(new HostAndPort("192.168.63.130",6379));
		hosts.add(new HostAndPort("192.168.63.130",6380));
		hosts.add(new HostAndPort("192.168.63.131",6381));
		hosts.add(new HostAndPort("192.168.63.131",6382));
		hosts.add(new HostAndPort("192.168.63.132",6383));
		hosts.add(new HostAndPort("192.168.63.132",6384));
		
		//创建集群版jedis客户端
		JedisCluster jedisCluster = new JedisCluster(hosts, config);
		jedisCluster.set("赵六","aaabbb");
		String value = jedisCluster.get("赵六");
		System.out.println(value);
	}
	
	@Autowired
	JedisCluster jedisCluster;
	@Test
	public void demo6(){
		String value = jedisCluster.get("赵六");
		System.out.println(value);
	}
	
	@Test
	public void demo7(){
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(50);
		Set<HostAndPort> hosts = new HashSet<HostAndPort>();
		hosts.add(new HostAndPort("192.168.63.130",6379));
		hosts.add(new HostAndPort("192.168.63.130",6380));
		hosts.add(new HostAndPort("192.168.63.131",6381));
		hosts.add(new HostAndPort("192.168.63.131",6382));
		hosts.add(new HostAndPort("192.168.63.132",6383));
		hosts.add(new HostAndPort("192.168.63.132",6384));
		JedisCluster jedisCluster = new JedisCluster(hosts,config);
		jedisCluster.set("王麻子","abc");
		String string = jedisCluster.get("王麻子");
		System.out.println(string);
	}
	
	@Test
	public void demo8(){
		String string = jedisCluster.get("zhangsan");
		System.out.println(string);
	}
	
	
}
