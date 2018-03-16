package com.myhope.action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.myhope.model.base.SessionInfo;
import com.myhope.model.easyui.Json;
import com.myhope.service.InitServiceI;
import com.myhope.util.base.ApiUtil;
import com.myhope.util.base.ConfigUtil;

/**
 * 初始化数据库使用
 * 
 * @author YangMing
 * 
 */
@Namespace("/")
@Action
public class InitAction extends BaseAction<Object> {
	private static final long serialVersionUID = 4997538693105414574L;

	@Autowired
	private InitServiceI service;

	synchronized public void nssnsc_initDb() {
		Json j = new Json();
		service.initDb();
		j.setSuccess(true);
		writeJson(j);

		if (getSession() != null) {
			getSession().invalidate();
		}
	}

	public void nssnsc_robot() {
		// String httpUrl = "http://apis.baidu.com/turing/turing/turing";
		String httpUrl = "http://www.tuling123.com/openapi/api";
		SessionInfo sessionInfo = (SessionInfo) getSession().getAttribute(ConfigUtil.getSessionInfoName());
		Map<String, String> httpArgMap = new HashMap<String, String>();
		httpArgMap.put("key", "d7969fc607b9f8597781f919ae362ef4");
		httpArgMap.put("userid", "zy" + sessionInfo.getUser().getId());
		try {
			httpArgMap.put("info", URLEncoder.encode(id, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String jsonResult = ApiUtil.request(httpUrl, httpArgMap);

		writeString(jsonResult);
	}

}
