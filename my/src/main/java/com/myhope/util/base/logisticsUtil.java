package com.myhope.util.base;

import java.util.UUID;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;

public class logisticsUtil {

//	public static void main(String[] args){
//		System.out.println(getLogisticsInfo("426442069336"));
//		System.out.println(getLogisticsName("426442069336"));
//	}

	/**
	 * 获取商品物流信息
	 * 
	 * @param postid ： 快递单号
	 * @return
	 */
	public static String getLogisticsInfo(String postid) {
		if(StringUtils.isBlank(postid)){
			return "";
		}
		
		String response = null;
		try {
			String url = "http://www.kuaidi100.com/query?type=" + getLogisticsName(postid) + "&postid=" + postid;
			HttpClient httpClient = new HttpClient();

			HttpMethod method = postMethod(url);

			httpClient.executeMethod(method);

			response = method.getResponseBodyAsString();
		} catch (Exception e) {
			return "";
		}

		return response;
	}

	/**
	 * 获取快递公司名称
	 * 
	 * @param postid ： 快递单号
	 * @return
	 */
	public static String getLogisticsName(String postid) {
		if(StringUtils.isBlank(postid)){
			return "";
		}
		
		String response = null;
		String result = null;
		try {
			String url = "http://www.kuaidi100.com/autonumber/autoComNum?resultv2=1&text=" + postid;
			HttpClient httpClient = new HttpClient();

			HttpMethod method = postMethod(url);

			httpClient.executeMethod(method);

			response = method.getResponseBodyAsString();
			result = response.substring(response.indexOf("[{\"comCode\"") + 13, response.indexOf("\",\"id\""));
		} catch (Exception e) {
			return "";
		}
		return result;
	}

	public static HttpMethod postMethod(String url) {
		PostMethod post = new PostMethod(url);
		post.setRequestHeader("User-Agent", UUID.randomUUID().toString());
		post.releaseConnection();
		return post;
	}
}
