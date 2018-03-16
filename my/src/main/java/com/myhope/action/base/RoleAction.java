package com.myhope.action.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.myhope.action.BaseAction;
import com.myhope.model.base.SessionInfo;
import com.myhope.model.base.TRole;
import com.myhope.model.base.TUser;
import com.myhope.model.easyui.Grid;
import com.myhope.model.easyui.Json;
import com.myhope.service.base.RoleServiceI;
import com.myhope.service.base.UserServiceI;
import com.myhope.util.base.ConfigUtil;
import com.myhope.util.base.HqlFilter;

/**
 * 角色
 * 
 * @author YangMing
 * 
 */
@Namespace("/base")
@Action
public class RoleAction extends BaseAction<TRole> {
	private static final long serialVersionUID = -2755911945004793768L;

	@Autowired
	private UserServiceI userService;

	/**
	 * 注入业务逻辑，使当前action调用service.xxx的时候，直接是调用基础业务逻辑
	 * 
	 * 如果想调用自己特有的服务方法时，请使用((TServiceI) service).methodName()这种形式强转类型调用
	 * 
	 * @param service
	 */
	@Autowired
	public void setService(RoleServiceI service) {
		this.service = service;
	}

	/**
	 * 角色grid
	 */
	public void grid() {
		Grid grid = new Grid();
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		SessionInfo sessionInfo = (SessionInfo) getSession().getAttribute(ConfigUtil.getSessionInfoName());
		hqlFilter.addFilter("QUERY_user#id_S_EQ", sessionInfo.getUser().getId());
		grid.setTotal(((RoleServiceI) service).countRoleByFilter(hqlFilter));
		grid.setRows(((RoleServiceI) service).findRoleByFilter(hqlFilter, page, rows));
		writeJson(grid);
	}

	/**
	 * 保存一个角色
	 */
	public void save() {
		Json json = new Json();
		if (data != null) {
			SessionInfo sessionInfo = (SessionInfo) getSession().getAttribute(ConfigUtil.getSessionInfoName());
			((RoleServiceI) service).saveRole(data, sessionInfo.getUser().getId());
			json.setSuccess(true);
		}
		writeJson(json);
	}

	/**
	 * 角色授权
	 */
	public void grant() {
		Json json = new Json();
		((RoleServiceI) service).grant(id, ids);
		json.setSuccess(true);
		writeJson(json);
	}

	/**
	 * 获得当前用户能看到的所有角色树
	 */
	public void nsc_getRolesTree() {
		SessionInfo sessionInfo = (SessionInfo) getSession().getAttribute(ConfigUtil.getSessionInfoName());
		TUser user = userService.getById(sessionInfo.getUser().getId());
		Set<TRole> roles = user.getRoles();
		List<TRole> l = new ArrayList<TRole>(roles);
		Collections.sort(l, new Comparator<TRole>() {// 排序
			@Override
			public int compare(TRole o1, TRole o2) {
				if (o1.getSeq() == null) {
					o1.setSeq(1000);
				}
				if (o2.getSeq() == null) {
					o2.setSeq(1000);
				}
				return o1.getSeq().compareTo(o2.getSeq());
			}
		});
		writeJson(l);
	}

	/**
	 * 获得当前用户的角色
	 */
	public void nsc_getRoleByUserId() {
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		hqlFilter.addFilter("QUERY_user#id_S_EQ", id);
		List<TRole> roles = ((RoleServiceI) service).findRoleByFilter(hqlFilter);
		writeJson(roles);
	}

	/**
	 * 用户角色分布报表
	 */
	public void nsc_userRoleChart() {
		List<TRole> roles = service.find();
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		for (TRole role : roles) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("name", role.getName());
			m.put("y", userService.countUserByRoleId(role.getId()));
			m.put("sliced", false);
			m.put("selected", false);
			l.add(m);
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("name", "无");
		m.put("y", userService.countUserByNotRoleId());
		m.put("sliced", true);
		m.put("selected", true);
		l.add(m);
		writeJson(l);
	}
}
