package com.myhope.action.member;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.myhope.action.BaseAction;
import com.myhope.model.easyui.Grid;
import com.myhope.model.easyui.Json;
import com.myhope.model.member.TMember;
import com.myhope.service.member.MemberServiceI;
import com.myhope.util.base.BeanUtils;
import com.myhope.util.base.EncryptUtil;
import com.myhope.util.base.HqlFilter;

/**
 * @author Cuiys 会员
 * @ClassName:MemberAction
 * @Version 版本
 * @ModifiedBy 修改人
 * @Copyright zhengyu
 * @date 2018年2月2日 下午1:36:05
 */
@Namespace("/member")
@Action
public class MemberAction extends BaseAction<TMember> {

	private static final long serialVersionUID = 1L;

	private MemberServiceI service;

	/**
	 * 注入业务逻辑，使当前action调用service.xxx的时候，直接是调用基础业务逻辑
	 * <p>
	 * 如果想调用自己特有的服务方法时，请使用((TServiceI) service).methodName()这种形式强转类型调用
	 * 
	 * @param service
	 */
	@Autowired
	public void setService(MemberServiceI service) {
		this.service = service;
	}

	@Override
	public void grid() {
		Grid grid = new Grid();
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		grid.setTotal(service.countByFilter(hqlFilter));
		grid.setRows(service.findByFilter(hqlFilter, page, rows));
		writeJson(grid);
	}

	public void getById() {
		if (!StringUtils.isBlank(id)) {
			writeJson(service.getById(id));
		} else {
			Json j = new Json();
			j.setMsg("主键不可为空！");
			writeJson(j);
		}
	}

	@Override
	public void update() {
		Json json = new Json();
		if (data != null && !StringUtils.isBlank(data.getId())) {
			TMember member = service.getById(data.getId());
			if (member == null) {
				json.setMsg("更新会员失败，会员编号不存在！");
				json.setSuccess(false);
			} else {
				boolean modifyPwd = false;
				TMember t = service.getById(data.getId());
				if (!data.getPwd().equals(t.getPwd())) {
					modifyPwd = true;
				}
				BeanUtils.copyNotNullProperties(data, t, new String[] {
						"createdatetime", "pwd" });
				if (modifyPwd) {
					t.setPwd(EncryptUtil.md5(data.getPwd()));
				}
				t.setUpdatedatetime(new Date());
				service.update(t);
				json.setMsg("更新成功！");
				json.setSuccess(true);
			}
		} else {
			json.setMsg("更新失败！");
		}
		writeJson(json);

	}
}
