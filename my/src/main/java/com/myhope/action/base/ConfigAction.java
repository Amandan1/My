package com.myhope.action.base;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.myhope.util.base.FileUploadUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.myhope.action.BaseAction;
import com.myhope.model.base.SessionInfo;
import com.myhope.model.base.TConfig;
import com.myhope.model.easyui.Json;
import com.myhope.service.base.ConfigServiceI;
import com.myhope.util.base.ConfigUtil;

/**
 * metadata
 * action访问地址是/base/config.myhope
 * @author YangMing
 */
@Namespace("/base")
@Action
public class ConfigAction extends BaseAction<TConfig> {
	private static final long serialVersionUID = 5894542427891106531L;
	/**
	 * 注入业务逻辑，使当前action调用service.xxx的时候，直接是调用基础业务逻辑
	 * 
	 * 如果想调用自己特有的服务方法时，请使用((TServiceI) service).methodName()这种形式强转类型调用
	 * 
	 * @param service
	 */
	@Autowired
	public void setService(ConfigServiceI service) {
		this.service = service;
	}
	/**
	 * 查询
	 */
	public void nssnsc_findByKey() {
		String key = getRequest().getParameter("key");
		if (StringUtils.isNotBlank(key)) {
			String val = ((ConfigServiceI) service).getConfigVal(data.getKey());
			writeJson(val);
		}else{
			writeJson("");
		}
	}

	@Override
	public void save() {
		data.setCreatedatetime(new Date());
		SessionInfo sessionInfo = (SessionInfo) getSession().getAttribute(ConfigUtil.getSessionInfoName());
		data.setCreateid(sessionInfo.getUser().getId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("key", data.getKey());
		TConfig config = service.getByHql("from TConfig where key=:key", params);
		Json json = new Json();
		if (config != null) {
			json.setSuccess(false);
			json.setMsg("参数编码不能重复！");
			writeJson(json);
			return;
		}
		super.save();
	}
	@Override
	public void update() {
		data.setUpdatedatetime(new Date());
		SessionInfo sessionInfo = (SessionInfo) getSession().getAttribute(ConfigUtil.getSessionInfoName());
		data.setUpdateid(sessionInfo.getUser().getId());
		super.update();
	}

	/**
	 * 上传图片
	 */
	private File file;
	private String fileFileName;    //文件名

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public void uploadImg() {
		String httpPath = FileUploadUtil.fileUpload(fileFileName, file);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("status", true);
		m.put("fileUrl", httpPath);
		try {
			getResponse().getWriter().write(JSON.toJSONString(m));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
