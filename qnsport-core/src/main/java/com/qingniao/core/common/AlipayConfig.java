package com.qingniao.core.common;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016092300575645";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCUKiPhYuNGUPuejaPtM2GQhJZDHct2W3MbLpNoTQJgJS1Gk748rB+DxIf9kDSqOYMvc+2TTRc17GUkMfs+UV73VAiFhWJAyLUmTR3X/mX8vLB00wuSn6wTGMUH0NtuI8pclPEnMlijnRDCjOD7JCIldBqPAr74ltcOktv1pdQpKK/q1uWKQAAgEI63gHLqCF7goFRxUPo+uAz8mgdo0VOG3JrIAlPWDf45/YU/GdwNcK56bwS/jxQYefeDSSQ4PlyatingerfsaU9drg+eJVAKjL1NZlo7TGoczywtO7HWr8BxGZQHJPqGjfEwPONK7RSKbCaUza68uZ2KZ4iRbVDlAgMBAAECggEAA6T1tPmFtZkQV1oMii0VXUSlnYkCFCszrZt7rLkYMd04O+AucbeaHteasLX3VaiMfYJQeasPwxfJdRZ1o5bEyziZ7Q7W19kX+5UyAroTMCBEBtuaEUU0KlQVpJeSjib7N0PCmsA2Re/7FY35irPc0ycD8DiVEfBD4evi1Sv7B/gajGAQnZtI1hXKycQQPc+TKu9N6Cg3+oUaKfWKAjAKCwYHQEVA9AdYcTP3B4eaje51Qt2ovUQ3JU0wqPqpnIv9GYwhKElMDoW2PiAhr1Ha2BFBszJApvzUsN9lacMZZaxOaZlOUKbdxfB3QzZ1JFzmmmKCKnAHkCgwLJBhXjJeEQKBgQDhlqqFf1LjrEOLHJO+1TZrtkBbMmff0cn8tfgHs2kXmh5xr3UE4GcrPYaw0d0Ns9SGYDsbmL9xZvDrVv7J4TNFrcx+/nQHMIUG4nneCY9RSuzOdvDR1aTNdMMXh3e39zfxwnfoEyWqYh9zxZN0MUwywiB/l4D9C6HqAqTRGx7FtwKBgQCoI3r8rjKZYFOLnaXfnWzKv0NBHOfPqwLq8jGROJNkuoxAPAuGqBr/XZcwnbuFsHn7bPRzKxspwf+GbdPJob88KRCfYfQ2zCCCUEQazI9Mk8cJchlymiTN7y4FN8nTotmevKbEaplKTBiKzsGEQA2r5pw9Y9vwjOxz4yWBsWz+QwKBgD/JXXAf/20XEYYtT21/VkMAppPi3dJAvK9zOK3cwhOFZaRZq2n0ldd5LzOiM7IjLi3M2gnEmCVVqTuyPpsQzXGfYhpqkp5RztuSgEkTRO4ElI8QvKloko/xuxRVHrnq8muuxD7f4x+ZXTFcJYSOR/B2ScAqM7PZbTABDV9/HqVjAoGAdvhD+a64ltYVx2BREhyt8ffLerMdQ0/ZzKx6bir9//QPcRQLg8hG8hIi/H9fRIVOipzZivQhUDa4u0HS7YTJb6QO4vfIeU2OYyawSdYcPy8BUqmtX+25xQ1MHt0OrfU4zpzl020p8exOIUmSnCVGhy+xta6G3XqZhJZg35fPa0kCgYA+KU9xXiME37QiIeyl3r/Y3pvjAb5t5az32Hj9su2OO36i/mdX1NwhGnRZivuobHu5Wn6OhwmYcavOB3zxEx6KlidfEYP+/RTaU6RI3PssyBjmVwR7Eyo1ynsLXw4EleADwe2rNiGO5lPmrH9nKh1jgxioEkL4kg+awz6GVpkJRg==";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtURGCnXJxjOeum1I79Wx58jJ8vnwx4nJgUfDS6FdIJTjf29IL/X3GOcPAKIf3sdQvk8RHO3cc4ybTD4Ggc3FKIlthpqFbfQs+8c5tXbxe3+6r2C+Zg9n/Dz+eB13uB79OOghwP0BRIkjfQqY2JvniNgwz8d3ydgcFpUKjZR9eJcwCluMdh16eoJ5ae+bt4PiccN7KdrQgkRygUF/3xnh4WtM5hxEcWscrD3ccqEFrUfI7FIgJRb5UPIFv9MX4zUz1bsWwuoAdR2umIiaeN/WHOQ5aStZuodmnqkgIw6nj41A86ewPGjJ611SWtHoBz6CiKu7+Sk2nL3OIVPVqS3wPwIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://localhost:8081/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://localhost:8081/backpage.html";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

