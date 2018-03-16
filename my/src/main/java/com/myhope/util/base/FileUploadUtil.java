package com.myhope.util.base;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.alibaba.druid.util.Base64;

/**
 * API工具类
 * 
 * @author YangMing
 * 
 */
public class FileUploadUtil {

//	public static void main(String[] args) {
//		String str = fileUpload(new File("D:\\test.jpg"));
//		System.out.println(str);
//	}

	public static String fileUpload(File file) {
		String fileBase64 = encodeBase64File(file);
		return fileUpload(fileBase64);
	}

	public static String fileUpload(String fileName, File file) {
		String fileBase64 = encodeBase64File(file);
		return fileUpload(fileName, fileBase64);
	}

	public static String fileUpload(String fileBase64) {
		return fileUpload(null, fileBase64);
	}

	@SuppressWarnings("deprecation")
	public static String fileUpload(String fileName, String fileBase64) {
		String info = null;
		try {
			HttpClient httpclient = new HttpClient();

			PostMethod post = new PostMethod("http://" + ConfigUtil.get("uploadServer"));
			// PostMethod post = new PostMethod("http://" + "192.168.0.39/upload/");
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			String sTelContent = "";
			if (fileName == null) {
				sTelContent = "{\"body\":\"" + fileBase64 + "\"}";
			} else {
				sTelContent = "{\"filename\":\"" + fileName + "\",\"body\":\"" + fileBase64 + "\"}";
			}
			post.setRequestBody(sTelContent);
			httpclient.executeMethod(post);
			info = new String(post.getResponseBody(), "utf-8");
		} catch (Exception e) {
			return "-1";
		}
		return info;
	}

	private static String encodeBase64File(File file) {
		FileInputStream inputFile = null;
		byte[] buffer = new byte[(int) file.length()];
		try {
			inputFile = new FileInputStream(file);
			inputFile.read(buffer);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				inputFile.close();
			} catch (Exception e) {
			}
		}

		return Base64.byteArrayToBase64(buffer);
	}

}
