package com.myhope.service.member.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.myhope.model.member.TMember;
import com.myhope.service.impl.BaseServiceImpl;
import com.myhope.service.member.MemberServiceI;

/**
 * 会员业务逻辑
 *
 * @author Zhangym
 */
@Service
public class MemberServiceImpl extends BaseServiceImpl<TMember> implements MemberServiceI {

	public List<TMember> memberSignCount(String currentMonth, String oldMonth) {
		String hql = "SELECT count(*),DATE_FORMAT(c_createdatetime,'%Y-%m') FROM T_MEMBER where "
				+ "c_createdatetime between :oldMonth and :currentMonth GROUP BY DATE_FORMAT(c_createdatetime,'%Y-%m')";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("currentMonth", currentMonth);
		params.put("oldMonth", oldMonth);
		List<Map> mapList = findBySql(hql, params);
		List<TMember> memberList = new ArrayList<TMember>();
		if (mapList.size() != 0) {
			for (int i = 0; i < mapList.size(); i++) {
				Map map = mapList.get(i);
				String count = map.get("count(*)").toString();
				String month = map.get("DATE_FORMAT(c_createdatetime,'%Y-%m')").toString();
				TMember member = new TMember();
				// member.setCount(Integer.parseInt(count));
				// member.setMonth(month);
				memberList.add(member);
			}
		}
		return memberList;
	}
 
}
