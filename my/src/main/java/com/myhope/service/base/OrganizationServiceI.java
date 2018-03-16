package com.myhope.service.base;

import java.util.List;

import com.myhope.model.base.TOrganization;
import com.myhope.service.BaseServiceI;
import com.myhope.util.base.HqlFilter;

/**
 * 机构业务
 * 
 * @author YangMing
 * 
 */
public interface OrganizationServiceI extends BaseServiceI<TOrganization> {

	/**
	 * 更新机构
	 */
	public void updateOrganization(TOrganization organization);

	/**
	 * 机构授权
	 * 
	 * @param id
	 *            机构ID
	 * @param resourceIds
	 *            资源IDS
	 */
	public void grant(String id, String resourceIds);

	/**
	 * 根据用户查找机构
	 */
	public List<TOrganization> findOrganizationByFilter(HqlFilter hqlFilter);

	/**
	 * 保存机构
	 * 
	 * @param organization
	 *            机构信息
	 * @param userId
	 *            用户ID
	 */
	public void saveOrganization(TOrganization organization, String userId);

	/**
	 * 根据父机构查询子孙孙孙机构
	 *
	 * @param treeList, pid, 子孙结果
	 *            树集合，父机构ID
	 */
	public List<TOrganization> findOrgByPID(List<TOrganization> treeList, String pid, List<TOrganization> childOrg);

	/**
	 * 查询机构列表
	 *
	 */
	public List<TOrganization> findOrg();

}
