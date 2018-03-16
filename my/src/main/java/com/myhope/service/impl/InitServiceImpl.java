package com.myhope.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.myhope.dao.base.BaseDaoI;
import com.myhope.model.base.TConfig;
import com.myhope.model.base.TMetadata;
import com.myhope.model.base.TMetadataDetail;
import com.myhope.model.base.TOrganization;
import com.myhope.model.base.TResource;
import com.myhope.model.base.TResourcetype;
import com.myhope.model.base.TRole;
import com.myhope.model.base.TUser;
import com.myhope.service.InitServiceI;
import com.myhope.util.base.EncryptUtil;

/**
 * 初始化数据库
 * 
 * @author YangMing
 * 
 */
@Service
public class InitServiceImpl implements InitServiceI {

	private static final Logger logger = Logger.getLogger(InitServiceImpl.class);

	private static final String FILEPATH = "initDataBase.xml";

	@Autowired
	private BaseDaoI baseDao;

	@Override
	public synchronized void initDb() {
		try {
			Document document = new SAXReader()
					.read(Thread.currentThread().getContextClassLoader().getResourceAsStream(FILEPATH));

			initResourcetype(document);// 初始化资源类型

			initResource(document);// 初始化资源

			initRole(document);// 初始化角色

			initRoleResource(document);// 初始化角色和资源的关系

			initOrganization(document);// 初始化机构

			initOrganizationResource(document);// 初始化机构和资源的关系

			initUser(document);// 初始化用户

			initUserRole(document);// 初始化用户和角色的关系

			initUserOrganization(document);// 初始化用户和机构的关系

			initMetaData(document);// 初始化元数据

			initConfig(document);// 初始化系统参数

		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	private void initResourcetype(Document document) {
		List childNodes = document.selectNodes("//resourcetypes/resourcetype");
		for (Object obj : childNodes) {
			Node node = (Node) obj;
			TResourcetype type = (TResourcetype) baseDao.getById(TResourcetype.class, node.valueOf("@id"));
			if (type == null) {
				type = new TResourcetype();
			}
			type.setId(node.valueOf("@id"));
			type.setName(node.valueOf("@name"));
			type.setDescription(node.valueOf("@description"));
			logger.info(JSON.toJSONStringWithDateFormat(type, "yyyy-MM-dd HH:mm:ss"));
			baseDao.saveOrUpdate(type);
		}
	}

	private void initResource(Document document) {
		List childNodes = document.selectNodes("//resources/resource");
		for (Object obj : childNodes) {
			Node node = (Node) obj;
			TResource resource = (TResource) baseDao.getById(TResource.class, node.valueOf("@id"));
			if (resource == null) {
				resource = new TResource();
			}
			resource.setId(node.valueOf("@id"));
			resource.setName(node.valueOf("@name"));
			resource.setUrl(node.valueOf("@url"));
			resource.setDescription(node.valueOf("@description"));
			resource.setIconCls(node.valueOf("@iconCls"));
			resource.setSeq(Integer.parseInt(node.valueOf("@seq")));

			if (!StringUtils.isBlank(node.valueOf("@target"))) {
				resource.setTarget(node.valueOf("@target"));
			} else {
				resource.setTarget("");
			}

			if (!StringUtils.isBlank(node.valueOf("@pid"))) {
				resource.setResource((TResource) baseDao.getById(TResource.class, node.valueOf("@pid")));
			} else {
				resource.setResource(null);
			}

			Node n = node.selectSingleNode("resourcetype");
			TResourcetype type = (TResourcetype) baseDao.getById(TResourcetype.class, n.valueOf("@id"));
			if (type != null) {
				resource.setResourcetype(type);
			}

			logger.info(JSON.toJSONStringWithDateFormat(resource, "yyyy-MM-dd HH:mm:ss"));
			baseDao.saveOrUpdate(resource);
		}
	}

	private void initRole(Document document) {
		List childNodes = document.selectNodes("//roles/role");
		for (Object obj : childNodes) {
			Node node = (Node) obj;
			TRole role = (TRole) baseDao.getById(TRole.class, node.valueOf("@id"));
			if (role == null) {
				role = new TRole();
			}
			role.setId(node.valueOf("@id"));
			role.setName(node.valueOf("@name"));
			role.setDescription(node.valueOf("@description"));
			role.setSeq(Integer.parseInt(node.valueOf("@seq")));
			logger.info(JSON.toJSONStringWithDateFormat(role, "yyyy-MM-dd HH:mm:ss"));
			baseDao.saveOrUpdate(role);
		}
	}

	private void initRoleResource(Document document) {
		List<Node> childNodes = document.selectNodes("//roles_resources/role");
		for (Node node : childNodes) {
			TRole role = (TRole) baseDao.getById(TRole.class, node.valueOf("@id"));
			if (role != null) {
				role.setResources(new HashSet());
				List<Node> cNodes = node.selectNodes("resource");
				for (Node n : cNodes) {
					TResource resource = (TResource) baseDao.getById(TResource.class, n.valueOf("@id"));
					if (resource != null) {
						role.getResources().add(resource);
					}
				}
				logger.info(JSON.toJSONStringWithDateFormat(role, "yyyy-MM-dd HH:mm:ss"));
				baseDao.saveOrUpdate(role);
			}
		}

		TRole role = (TRole) baseDao.getById(TRole.class, "0");// 将角色为0的超级管理员角色，赋予所有权限
		if (role != null) {
			role.getResources().addAll(baseDao.find("from TResource t"));
			logger.info(JSON.toJSONStringWithDateFormat(role, "yyyy-MM-dd HH:mm:ss"));
			baseDao.saveOrUpdate(role);
		}
	}

	private void initOrganization(Document document) {
		List childNodes = document.selectNodes("//organizations/organization");
		for (Object obj : childNodes) {
			Node node = (Node) obj;
			TOrganization organization = (TOrganization) baseDao.getById(TOrganization.class, node.valueOf("@id"));
			if (organization == null) {
				organization = new TOrganization();
			}
			organization.setId(node.valueOf("@id"));
			organization.setName(node.valueOf("@name"));
			organization.setAddress(node.valueOf("@address"));
			organization.setSeq(Integer.parseInt(node.valueOf("@seq")));
			organization.setIconCls(node.valueOf("@iconCls"));
			organization.setOrganizationtype(node.valueOf("@organizationtype"));
			organization.setOrgmanager(node.valueOf("@orgmanager"));

			if (!StringUtils.isBlank(node.valueOf("@pid"))) {
				organization
						.setOrganization((TOrganization) baseDao.getById(TOrganization.class, node.valueOf("@pid")));
			} else {
				organization.setOrganization(null);
			}

			logger.info(JSON.toJSONStringWithDateFormat(organization, "yyyy-MM-dd HH:mm:ss"));
			baseDao.saveOrUpdate(organization);
		}
	}

	private void initOrganizationResource(Document document) {
		List<Node> childNodes = document.selectNodes("//organizations_resources/organization");
		for (Node node : childNodes) {
			TOrganization organization = (TOrganization) baseDao.getById(TOrganization.class, node.valueOf("@id"));
			if (organization != null) {
				organization.setResources(new HashSet());
				List<Node> cNodes = node.selectNodes("resource");
				for (Node n : cNodes) {
					TResource resource = (TResource) baseDao.getById(TResource.class, n.valueOf("@id"));
					if (resource != null) {
						organization.getResources().add(resource);
					}
				}
				logger.info(JSON.toJSONStringWithDateFormat(organization, "yyyy-MM-dd HH:mm:ss"));
				baseDao.saveOrUpdate(organization);
			}
		}
	}

	private void initUser(Document document) {
		List<Node> childNodes = document.selectNodes("//users/user");
		for (Node node : childNodes) {
			TUser user = (TUser) baseDao.getById(TUser.class, node.valueOf("@id"));
			if (user == null) {
				user = new TUser();
			}
			user.setId(node.valueOf("@id"));
			user.setName(node.valueOf("@name"));
			user.setLoginname(node.valueOf("@loginname"));
			user.setPwd(EncryptUtil.md5(node.valueOf("@pwd")));
			user.setSex(node.valueOf("@sex"));
			user.setAge(Integer.valueOf(node.valueOf("@age")));
			user.setCode(node.valueOf("@code"));
			user.setMobile(node.valueOf("@mobile"));
			logger.info(JSON.toJSONStringWithDateFormat(user, "yyyy-MM-dd HH:mm:ss"));
			List<TUser> ul = baseDao.find("from TUser u where u.loginname = '" + user.getLoginname() + "' and u.id != '"
					+ user.getId() + "'");
			for (TUser u : ul) {
				baseDao.delete(u);
			}
			baseDao.saveOrUpdate(user);
		}
	}

	private void initUserRole(Document document) {
		List<Node> childNodes = document.selectNodes("//users_roles/user");
		for (Node node : childNodes) {
			TUser user = (TUser) baseDao.getById(TUser.class, node.valueOf("@id"));
			if (user != null) {
				user.setRoles(new HashSet());
				List<Node> cNodes = node.selectNodes("role");
				for (Node n : cNodes) {
					TRole role = (TRole) baseDao.getById(TRole.class, n.valueOf("@id"));
					if (role != null) {
						user.getRoles().add(role);
					}
				}
				logger.info(JSON.toJSONStringWithDateFormat(user, "yyyy-MM-dd HH:mm:ss"));
				baseDao.saveOrUpdate(user);
			}
		}

		TUser user = (TUser) baseDao.getById(TUser.class, "0");// 用户为0的超级管理员，赋予所有角色
		if (user != null) {
			user.getRoles().addAll(baseDao.find("from TRole"));
			logger.info(JSON.toJSONStringWithDateFormat(user, "yyyy-MM-dd HH:mm:ss"));
			baseDao.saveOrUpdate(user);
		}
	}

	private void initUserOrganization(Document document) {
		List<Node> childNodes = document.selectNodes("//users_organizations/user");
		for (Node node : childNodes) {
			TUser user = (TUser) baseDao.getById(TUser.class, node.valueOf("@id"));
			if (user != null) {
				user.setOrganizations(new HashSet());
				List<Node> cNodes = node.selectNodes("organization");
				for (Node n : cNodes) {
					TOrganization organization = (TOrganization) baseDao.getById(TOrganization.class, n.valueOf("@id"));
					if (organization != null) {
						user.getOrganizations().add(organization);
					}
				}
				logger.info(JSON.toJSONStringWithDateFormat(user, "yyyy-MM-dd HH:mm:ss"));
				baseDao.saveOrUpdate(user);
			}
		}

		TUser user = (TUser) baseDao.getById(TUser.class, "0");// 用户为0的超级管理员，赋予所有机构
		if (user != null) {
			user.getOrganizations().addAll(baseDao.find("from TOrganization"));
			logger.info(JSON.toJSONStringWithDateFormat(user, "yyyy-MM-dd HH:mm:ss"));
			baseDao.saveOrUpdate(user);
		}
	}

	private void initMetaData(Document document) {
		List<Node> childNodes = document.selectNodes("//metadatas/metadata");
		for (Node node : childNodes) {
			TMetadata metadata = (TMetadata) baseDao.getById(TMetadata.class, node.valueOf("@id"));
			if (metadata == null) {
				metadata = new TMetadata();
				metadata.setId(node.valueOf("@id"));
				metadata.setName(node.valueOf("@name"));
				metadata.setContent(node.valueOf("@content"));
				metadata.setRemark(node.valueOf("@remark"));
				metadata.setType(node.valueOf("@type"));

				List<Node> metadata_details = node.selectNodes("metadata_detail");
				for (Node node2 : metadata_details) {
					TMetadataDetail metadataDetail = (TMetadataDetail) baseDao.getById(TMetadataDetail.class,
							node2.valueOf("@id"));
					if (metadataDetail == null) {
						metadataDetail = new TMetadataDetail();
						metadataDetail.setId(node2.valueOf("@id"));
						metadataDetail.setKey(node2.valueOf("@key"));
						metadataDetail.setSeq(Integer.parseInt(node2.valueOf("@seq")));
						metadataDetail.setVal(node2.valueOf("@val"));
						metadataDetail.setRemark(node2.valueOf("@remark"));
						metadataDetail.setMetadata(metadata);

						baseDao.saveOrUpdate(metadataDetail);
					}
				}
				baseDao.saveOrUpdate(metadata);
			}
		}
	}

	private void initConfig(Document document) {
		List childNodes = document.selectNodes("//configs/config");
		for (Object obj : childNodes) {
			Node node = (Node) obj;
			TConfig config = (TConfig) baseDao.getById(TConfig.class, node.valueOf("@id"));
			if (config == null) {
				config = new TConfig();
			}
			config.setId(node.valueOf("@id"));
			config.setKey(node.valueOf("@key"));
			config.setName(node.valueOf("@name"));
			config.setVal(node.valueOf("@val"));
			config.setType((node.valueOf("@type") == null || "".equals(node.valueOf("@type")))? "1" : node.valueOf("@type"));
			config.setRemark(node.valueOf("@remark") == null ? "" : node.valueOf("@remark"));
			config.setCreatedatetime(new Date());
			config.setCreateid("0");
			config.setUpdatedatetime(new Date());
			config.setUpdateid("0");
			
			baseDao.saveOrUpdate(config);
		}
	}

}
