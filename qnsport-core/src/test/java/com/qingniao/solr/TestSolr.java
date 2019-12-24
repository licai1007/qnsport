package com.qingniao.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-*.xml")
public class TestSolr {
	@Test
	public void demo1() throws SolrServerException, IOException{
		HttpSolrServer httpSolrServer = new HttpSolrServer("http://192.168.182.133:8080/solr");
		SolrInputDocument sInputDocument = new SolrInputDocument();
		sInputDocument.addField("id",5);
		sInputDocument.addField("name","张三");
		httpSolrServer.add(sInputDocument);
		httpSolrServer.commit();
	}
	@Test
	public void demo2() throws SolrServerException{
		HttpSolrServer hServer = new HttpSolrServer("http://192.168.182.133:8080/solr");
		SolrQuery sQuery = new SolrQuery();
		sQuery.set("q","*:*");
		QueryResponse query = hServer.query(sQuery);
		SolrDocumentList results = query.getResults();
		for (SolrDocument solrDocument : results) {
			String id = (String) solrDocument.get("id");
			String name = (String) solrDocument.get("name");
			System.out.println(id+"    "+name);
		}
	}
	@Autowired
	private HttpSolrServer server;
	@Test
	public void demo3() throws SolrServerException, IOException{
		server.deleteById("10004");
		server.commit();
	}
	@Test
	public void demo4() throws SolrServerException, IOException{
		SolrInputDocument sDocument  = new SolrInputDocument();
		sDocument.setField("id",1);
		sDocument.setField("name","赵六");
		server.add(sDocument);
		server.commit();
	}
	//集群版solr的测试
	@Test
	public void demo5() throws SolrServerException, IOException{
		//配置三台zookeeper的ip
		String zHost = "192.168.63.130:2181,192.168.63.131:2181,192.168.63.132:2181";
		//创建集群版solr客户端
		CloudSolrServer solrServer = new CloudSolrServer(zHost);
		//设置默认collection1
		solrServer.setDefaultCollection("collection1");
		
		//保存数据
		SolrInputDocument doc = new SolrInputDocument();
		doc.setField("id","52");
		doc.setField("name","刘癞子");
		solrServer.add(doc);
		solrServer.commit();
	}
	@Autowired
	CloudSolrServer solrServer;
	@Test
	public void demo6() throws SolrServerException, IOException{
		SolrInputDocument doc = new SolrInputDocument();
		doc.setField("id","65");
		doc.setField("name","怪怪");
		solrServer.add(doc);
		solrServer.commit();
	}

}