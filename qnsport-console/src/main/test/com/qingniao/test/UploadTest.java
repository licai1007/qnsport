package com.qingniao.test;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qingniao.core.common.FastDFSClient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-*.xml")
public class UploadTest {
	@Test
	public void upload1(){
		try {
			ClientGlobal.init("F:/qnds/qingniaosport/parent/qnsport-console/src/main/resources/properties/fastdfs.properties");
			TrackerClient trackerClient = new TrackerClient();
			TrackerServer trackerServer = trackerClient.getConnection();
			StorageServer storageServer = null;
			StorageClient1 storageClient1 = new StorageClient1(trackerServer, storageServer);
			String[] str = storageClient1.upload_file("C:/Users/Public/Pictures/Sample Pictures/Koala.jpg","jpg",null);
			for (String string : str) {
				System.out.println(string);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}
	}
		@Test
		public void upload2(){
			try {
				FastDFSClient fastDFSClient = new FastDFSClient("F:/qnds/qingniaosport/parent/qnsport-console/src/main/resources/properties/fastdfs.properties");
				String file = fastDFSClient.uploadFile("C:/Users/Public/Pictures/Sample Pictures/Chrysanthemum.jpg");
				System.out.println(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

}
