package com.myhope.service.member;

import java.util.List;

import com.myhope.model.member.TMember;
import com.myhope.service.BaseServiceI;


 
/**@author Cuiys	
 * @ClassName:MemberServiceI
 * @Version 会员管理service
 * @ModifiedBy 修改人
 * @Copyright 公司名称
 * @date 2018年2月2日 下午1:46:22
 */
public interface MemberServiceI extends BaseServiceI<TMember> {
	
    public List<TMember> memberSignCount(String currentMonth, String oldMonth);


}
