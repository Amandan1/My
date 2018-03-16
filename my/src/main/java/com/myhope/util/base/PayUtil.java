package com.myhope.util.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import com.myhope.util.base.wxpay.XMLUtil;

/**
 * 功能：微信支付退款
 */
public class PayUtil {
	public static final String APPID = "wx8a58f63351c94f4d";

	public static final String SECRET = "c9b58f41801b7c6600dacfe4e0ad7a3c";

	public static final String MCH_ID = "1494525022"; // 商业号

	public static final String MCH_KEY = "dldkhfgjklFLKHJLFKHhgkdfkdfhgkld"; // 商业密钥

	public static final String TICKET_LOGIN_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8a58f63351c94f4d&redirect_uri=http://shop.qiante333.com/ZTMall/index/page&response_type=code&scope=snsapi_base&state=";

	public static final String TXDT_KEY = "HE7BZ-I7IR3-XDE35-YJZHJ-4ETAJ-VAB3J";
	
	public static final String REFUND_URL= "https://api.mch.weixin.qq.com/secapi/pay/refund";

	public static String createSign(String charSet, SortedMap<Object, Object> parameters) {
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + MCH_KEY);
		String sign = EncryptUtil.md5(sb.toString());
		return sign;
	}

	public static String getRequestXml(SortedMap<Object, Object> parameters) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {
				sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
			} else {
				sb.append("<" + k + ">" + v + "</" + k + ">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}
	public static String refund(String orderNumber,String totalMoney,String money)  throws Exception{
		String result ="FAIL";
		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("appid", PayUtil.APPID);// appid
		parameters.put("mch_id", PayUtil.MCH_ID);// 商户号
		parameters.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0, 16));
		// 在notify_url中解析微信返回的信息获取到 transaction_id，此项不是必填，详细请看上图文档
		// parameters.put("transaction_id", "微信支付订单中调用统一接口后微信返回的
		// transaction_id");
		parameters.put("out_trade_no", orderNumber);// 订单好
		parameters.put("out_refund_no", orderNumber);// 我们自己设定的退款申请号，约束为UK
		parameters.put("total_fee", totalMoney);// 单位为分
		parameters.put("refund_fee", money);// 单位为分
		String sign = createSign("utf-8", parameters);
		parameters.put("sign", sign);

		String reuqestXml = getRequestXml(parameters);
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		FileInputStream instream = new FileInputStream(new File(PayUtil.class.getResource("/").getFile()+"apiclient_cert.p12"));// 放退款证书的路径
		try {
			keyStore.load(instream, PayUtil.MCH_ID.toCharArray());// "123456"指的是商户号
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			instream.close();
		}

		SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, PayUtil.MCH_ID.toCharArray()).build();// "123456"指的是商户号
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.getDefaultHostnameVerifier());
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		try {

			HttpPost httpPost = new HttpPost(PayUtil.REFUND_URL);// 退款接口

			System.out.println("executing request" + httpPost.getRequestLine());
			StringEntity reqEntity = new StringEntity(reuqestXml);
			// 设置类型
			reqEntity.setContentType("application/x-www-form-urlencoded");
			httpPost.setEntity(reqEntity);
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();

				if (entity != null) {
					
					BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(entity.getContent(), "UTF-8"));
					
					StringBuffer sb = new StringBuffer();
			        //解析xml成map
			        Map<String, String> m = new HashMap<String, String>();
			        
					String text;
					while ((text = bufferedReader.readLine()) != null) {
						sb.append(text);
						System.out.println(text);
					}
					m = XMLUtil.doXMLParse(sb.toString());
					result = m.get("result_code").toUpperCase();
				}
				EntityUtils.consume(entity);
			} catch (Exception e) {
				 response.close();  
			}
		} finally {
			httpclient.close();
		}
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		refund("0", "0", "0");
	}

}
