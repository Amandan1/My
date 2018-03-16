package com.myhope.util.base;

import java.util.ResourceBundle;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myhope.service.base.ConfigServiceI;

/**
 * 项目参数工具类
 * 
 * @author YangMing
 * 
 */
@Component
public class ConfigUtil {

	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("config");

	private static ConfigUtil configUtil;

	@Autowired
	private ConfigServiceI configService;

	public void setConfigService(ConfigServiceI configService) {
		this.configService = configService;
	}

	@PostConstruct
	public void init() {
		configUtil = this;
		configUtil.configService = this.configService;
	}

	/**
	 * 获得sessionInfo名字
	 * 
	 * @return
	 */
	public static final String getSessionInfoName() {
		return bundle.getString("sessionInfoName");
	}

	/**
	 * 通过键获取值
	 * 
	 * @param key
	 * @return
	 */
	public static final String get(String key) {
		String val = "";
		if (bundle.containsKey(key)) {
			val = bundle.getString(key);
		} else {
			val = configUtil.configService.getConfigVal(key);
		}

		return val;
	}

}
