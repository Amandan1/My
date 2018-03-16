package com.myhope.action.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
 
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.myhope.action.BaseAction;
import com.myhope.model.base.SessionInfo;
import com.myhope.model.base.TMetadata;
import com.myhope.model.base.TMetadataDetail;
import com.myhope.model.base.TUser;
import com.myhope.model.easyui.Grid;
import com.myhope.model.easyui.Json;
import com.myhope.service.base.MetadataDetaiServiceI;
import com.myhope.service.base.MetadataServiceI;
import com.myhope.util.base.BeanUtils;
import com.myhope.util.base.ConfigUtil;
import com.myhope.util.base.HqlFilter;

/**
 * metadata
 * 
 * action访问地址是/base/metadata.myhope
 * 
 * @author YangMing
 * 
 */
@Namespace("/base")
@Action
public class MetadataAction extends BaseAction<TMetadata> {
	private static final long serialVersionUID = -4343163043969293678L;

	/**
	 * 注入业务逻辑，使当前action调用service.xxx的时候，直接是调用基础业务逻辑
	 * 
	 * 如果想调用自己特有的服务方法时，请使用((TServiceI) service).methodName()这种形式强转类型调用
	 * 
	 * @param service
	 */
	@Autowired
	public MetadataDetaiServiceI metadataDetailService;
	
	@Autowired
	public void setService(MetadataServiceI service) {
		this.service = service;
	}
	

	/**
	 * 查询list
	 */
	public void findMetadataDetail() {
		TMetadata metadata = service.getById(id);

		List<TMetadataDetail> metadataDetails = new ArrayList<TMetadataDetail>(metadata.getMetadataDetails());

		Collections.sort(metadataDetails, new Comparator<TMetadataDetail>() {
			@Override
			public int compare(TMetadataDetail o1, TMetadataDetail o2) {
				if (o1.getSeq() == null) {
					o1.setSeq(1000);
				}
				if (o2.getSeq() == null) {
					o2.setSeq(1000);
				}
				return o1.getSeq().compareTo(o2.getSeq());
			}
		});
		writeJson(metadataDetails);
	}

	/**
	 * 查询list
	 */
	public void nssnsc_findByName() {
		SessionInfo sessionInfo = (SessionInfo) getSession()
				.getAttribute(ConfigUtil.getSessionInfoName());
		TUser user=sessionInfo.getUser();
		if (data != null && data.getName() != null) {
			
			TMetadata metadata = ((MetadataServiceI) service).findMetadataByName(data.getName());

			List<TMetadataDetail> metadataDetails = new ArrayList<TMetadataDetail>(metadata.getMetadataDetails());
				
				 for (int i=0;i<metadataDetails.size();i++) {
					 if(!"admin".equals(user.getLoginname())){
						 if("系统".equals(metadataDetails.get(i).getRemark())){
							 metadataDetails.remove(i);
						 }
				}  
				
			}
			Collections.sort(metadataDetails, new Comparator<TMetadataDetail>() {
				@Override
				public int compare(TMetadataDetail o1, TMetadataDetail o2) {
					if (o1.getSeq() == null) {
						o1.setSeq(1000);
					}
					if (o2.getSeq() == null) {
						o2.setSeq(1000);
					}
					return o1.getSeq().compareTo(o2.getSeq());
				}
			});
			writeJson(metadataDetails);
		}
	}
 

	public void nssnsc_find() {
		String name = getRequest().getParameter("name");
		String key = getRequest().getParameter("key");

		TMetadataDetail a = ((MetadataServiceI) service).findDetailByKey(name, key);
		if (a == null) {
			a = new TMetadataDetail();
		}

		writeJson(a);
	}
	public void nssnsc_findRemark() {
		String name = getRequest().getParameter("name");
		String remark = getRequest().getParameter("remark");
		
		TMetadataDetail a = ((MetadataServiceI) service).findDetailByKey(name, remark);
		if (a == null) {
			a = new TMetadataDetail();
		}
		
		writeJson(a);
	}

	@Override
	public void update() {
		Json json = new Json();
		String reflectId = null;
		try {
			if (data != null) {
				reflectId = (String) FieldUtils.readField(data, "id", true);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		if (!StringUtils.isBlank(reflectId)) {
			TMetadata t = service.getById(reflectId);
			BeanUtils.copyNotNullProperties(data, t, new String[] { "createdatetime" });
			List<TMetadataDetail> mdArray = JSON.parseArray(getRequest().getParameter("mdJson"), TMetadataDetail.class);
			t.setMetadataDetails(new HashSet<TMetadataDetail>(mdArray));
			service.update(t);
			json.setSuccess(true);
			json.setMsg("更新成功！");
		}
		writeJson(json);
	}

	@Override
	public void save() {
		Json json = new Json();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", data.getName());
		TMetadata metadata = service.getByHql("from TMetadata where name=:name", params);
		if (metadata != null) {
			json.setSuccess(false);
			json.setMsg("元数据编号不能重复！");
			writeJson(json);
			return;
		}

		if (data != null) {
			service.save(data);
			List<TMetadataDetail> mdArray = JSON.parseArray(getRequest().getParameter("mdJson"), TMetadataDetail.class);
			data.setMetadataDetails(new HashSet<TMetadataDetail>(mdArray));
			service.update(data);

			json.setSuccess(true);
			json.setMsg("新建成功！");
		}
		writeJson(json);
	}
	
	public void nssnsc_findByList(){
		HttpServletRequest request = getRequest();
		String data = request.getParameter("name");
		JSONArray json = JSONArray.parseArray(data);
		Iterator<Object> it = json.iterator();
		Map<String,String> map = new HashMap<>();
		while (it.hasNext()) {
			JSONObject ob = (JSONObject) it.next();
			String name =String.valueOf(ob.get("name")) ;
			String key = String.valueOf(ob.getString("key"));
			TMetadataDetail a = ((MetadataServiceI) service).findDetailByKey(name, key);
			if (a == null) {
				a = new TMetadataDetail();
			}
			map.put(a.getMetadata().getName(), a.getVal());
		}
		writeJson(map);	
	}
	
	@Override
	public  void  grid(){
		SessionInfo sessionInfo = (SessionInfo) getSession()
				.getAttribute(ConfigUtil.getSessionInfoName());
		TUser user=sessionInfo.getUser();
		Grid grid = new Grid();
		HqlFilter hqlFilter = new HqlFilter(getRequest());
			if("admin".equals(user.getLoginname())){
				grid.setTotal(service.countByFilter(hqlFilter));
				grid.setRows(service.findByFilter(hqlFilter, page, rows));
				writeJson(grid);
		}else{
			hqlFilter.addFilter("QUERY_t#type_S_EQ", "1");
			grid.setTotal(service.countByFilter(hqlFilter));
			grid.setRows(service.findByFilter(hqlFilter, page, rows));
			writeJson(grid);
		}
		
		
	}
	
	
}
