package com.myhope.action.base;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.myhope.action.BaseAction;
import com.myhope.model.base.SessionInfo;
import com.myhope.model.base.TOrganization;
import com.myhope.model.base.TRole;
import com.myhope.model.base.TUser;
import com.myhope.model.easyui.Grid;
import com.myhope.model.easyui.Json;
import com.myhope.model.easyui.Tree;
import com.myhope.model.member.TMember;
import com.myhope.service.base.OrganizationServiceI;
import com.myhope.service.base.RoleServiceI;
import com.myhope.service.base.UserServiceI;
import com.myhope.service.member.MemberServiceI;
import com.myhope.util.base.BeanUtils;
import com.myhope.util.base.ConfigUtil;
import com.myhope.util.base.EncryptUtil;
import com.myhope.util.base.FileUploadUtil;
import com.myhope.util.base.HqlFilter;
import com.myhope.util.base.IpUtil;

/**
 * 员工
 * <p/>
 * action访问地址是/base/user.myhope
 *
 * @author YangMing
 */
@Namespace("/base")
@Action
public class UserAction extends BaseAction<TUser> {
    private static final long serialVersionUID = -1286230355893173526L;

    @Autowired
    private OrganizationServiceI organizationService;
    @Autowired
    private RoleServiceI roleService;
    @Autowired
    private MemberServiceI memberService;

    /**
     * 注入业务逻辑，使当前action调用service.xxx的时候，直接是调用基础业务逻辑
     * <p/>
     * 如果想调用自己特有的服务方法时，请使用((TServiceI) service).methodName()这种形式强转类型调用
     *
     * @param service
     */
    @Autowired
    public void setService(UserServiceI service) {
        this.service = service;
    }

    /**
     * 注销系统
     */
    public void nssnsc_logout() {
        if (getSession() != null) {
            getSession().invalidate();
        }
        Json j = new Json();
        j.setSuccess(true);
        writeJson(j);
    }

    /**
     * 注册
     */
    synchronized public void nssnsc_reg() {
        Json json = new Json();
        HqlFilter hqlFilter = new HqlFilter();
        hqlFilter.addFilter("QUERY_t#loginname_S_EQ", data.getLoginname());
        TUser user = service.getByFilter(hqlFilter);
        if (user != null) {
            json.setMsg("员工名已存在！");
            writeJson(json);
        } else {
            TUser u = new TUser();
            BeanUtils.copyProperties(data, u);
            // 密码加密
            u.setPwd(EncryptUtil.md5(data.getPwd()));
            u.setCreatedatetime(new Date());
            // 添加默认注册机构
            TOrganization torganization = organizationService.getById("1");
            Set<TOrganization> organizations = new HashSet<TOrganization>(0);
            organizations.add(torganization);
            u.setOrganizations(organizations);
            // 添加默认注册角色
            TRole role = roleService.getById("1");
            Set<TRole> roles = new HashSet<TRole>(0);
            roles.add(role);
            u.setRoles(roles);
            // 入库
            service.save(u);
            nssnsc_login();
        }
    }

    /**
     * 登录
     */
    public void nssnsc_login() {
        HqlFilter hqlFilter = new HqlFilter();
        hqlFilter.addFilter("QUERY_t#loginname_S_EQ", data.getLoginname());
        TUser user = service.getByFilter(hqlFilter);
        Json json = new Json();
        if (user != null) {
            if (EncryptUtil.md5(data.getPwd()).equals(user.getPwd())) {
                json.setSuccess(true);

                SessionInfo sessionInfo = new SessionInfo();
                Hibernate.initialize(user.getRoles());
                Hibernate.initialize(user.getOrganizations());
                for (TRole role : user.getRoles()) {
                    Hibernate.initialize(role.getResources());
                }
                for (TOrganization organization : user.getOrganizations()) {
                    Hibernate.initialize(organization.getResources());
                }
                user.setIp(IpUtil.getIpAddr(getRequest()));
                sessionInfo.setUser(user);
                getSession().setAttribute(ConfigUtil.getSessionInfoName(), sessionInfo);
            } else {
                json.setMsg("登录名或密码错误！");
            }
        } else {
            json.setMsg("登录名不存在！");
        }
        writeJson(json);
    }

    /**
     * 修改自己的密码
     */
    public void nsc_updateCurrentPwd() {
        SessionInfo sessionInfo = (SessionInfo) getSession().getAttribute(ConfigUtil.getSessionInfoName());
        Json json = new Json();
        TUser user = service.getById(sessionInfo.getUser().getId());
        user.setPwd(EncryptUtil.md5(data.getPwd()));
        user.setUpdatedatetime(new Date());
        service.update(user);
        json.setSuccess(true);
        writeJson(json);
    }

    /**
     * 修改员工角色
     */
    public void grantRole() {
        Json json = new Json();
        ((UserServiceI) service).grantRole(id, ids);
        json.setSuccess(true);
        writeJson(json);
    }

    /**
     * 修改员工机构
     */
    public void grantOrganization() {
        Json json = new Json();
        ((UserServiceI) service).grantOrganization(id, ids);
        json.setSuccess(true);
        writeJson(json);
    }

    /**
     * 统计员工注册时间图表
     */
    public void nsc_userCreateDatetimeChart() {
        writeJson(((UserServiceI) service).userCreateDatetimeChart());
    }

    /**
     * 新建一个员工
     */
    synchronized public void save() {
        Json json = new Json();
        StringBuffer msg = new StringBuffer();
        msg.append("添加员工失败！");
        if (data != null) {
            HqlFilter hqlFilter_loginname = new HqlFilter();
            hqlFilter_loginname.addFilter("QUERY_t#loginname_S_EQ", data.getLoginname());
            TUser user_loginname = service.getByFilter(hqlFilter_loginname);
            if (user_loginname != null ) {
                if (user_loginname != null) {
                    msg.append("员工名已存在！");
                }
                json.setMsg(msg.toString());
            } else {
                data.setPwd(EncryptUtil.md5("123456"));
                data.setCode(data.getMobile());
                
              /*  Set<TOrganization> organizations = new HashSet<TOrganization>(0);
                TOrganization org = new TOrganization();
                org.setId("0");
                organizations.add(org);
                data.setOrganizations(organizations);*/
                service.save(data);
                json.setMsg("新建员工成功！默认密码：123456");
                json.setSuccess(true);
            }
        }
        writeJson(json);
    }

    /**
     * 更新一个员工
     */
    @Override
    synchronized public void update() {
        Json json = new Json();
        json.setMsg("更新失败！");
        if (data != null && !StringUtils.isBlank(data.getId())) {
            HqlFilter hqlFilter = new HqlFilter();
            hqlFilter.addFilter("QUERY_t#id_S_NE", data.getId());
            hqlFilter.addFilter("QUERY_t#loginname_S_EQ", data.getLoginname());
            TUser user = service.getByFilter(hqlFilter);
            if (user != null) {
                json.setMsg("更新员工失败，员工名已存在！");
            } else {
                TUser t = service.getById(data.getId());
                BeanUtils.copyNotNullProperties(data, t, new String[]{"createdatetime", "pwd"});
                service.update(t);
                json.setSuccess(true);
                json.setMsg("更新成功！");
            }
        }
        writeJson(json);
    }

    /**
     * 更新自己
     */
    synchronized public void nssnsc_update() {
        Json json = new Json();
        json.setMsg("更新失败！");
        if (data != null && !StringUtils.isBlank(data.getId())) {
            HqlFilter hqlFilter = new HqlFilter();
            hqlFilter.addFilter("QUERY_t#id_S_NE", data.getId());
            hqlFilter.addFilter("QUERY_t#loginname_S_EQ", data.getLoginname());
            TUser user = service.getByFilter(hqlFilter);
            if (user != null) {
                json.setMsg("更新员工失败，员工名已存在！");
            } else {
                TUser t = service.getById(data.getId());
                BeanUtils.copyNotNullProperties(data, t, new String[]{"createdatetime", "pwd"});
                service.update(t);
                json.setSuccess(true);
                json.setMsg("更新成功！");
            }
        }
        writeJson(json);
    }

    /**
     * 员工登录页面的自动补全
     */
    public void nssnsc_loginNameComboBox() {
        HqlFilter hqlFilter = new HqlFilter();
        hqlFilter.addFilter("QUERY_t#loginname_S_LK", "%%" + StringUtils.defaultString(q) + "%%");
        hqlFilter.addSort("t.loginname");
        hqlFilter.addOrder("asc");
        writeJsonByIncludesProperties(service.findByFilter(hqlFilter, 1, 10), new String[]{"loginname"});
    }

    /**
     * 员工登录页面的grid自动补全
     */
    public void nssnsc_loginNameComboGrid() {
        Grid grid = new Grid();
        HqlFilter hqlFilter = new HqlFilter(getRequest());
        hqlFilter.addFilter("QUERY_t#loginname_S_LK", "%%" + StringUtils.defaultString(q) + "%%");
        grid.setTotal(service.countByFilter(hqlFilter));
        grid.setRows(service.findByFilter(hqlFilter, page, rows));
        writeJson(grid);
    }

    /**
     * 根据员工机构,获得员工tree
     */
    public void nsc_getUsersTree() {
        SessionInfo sessionInfo = (SessionInfo) getSession().getAttribute(ConfigUtil.getSessionInfoName());
        TUser user = sessionInfo.getUser();
        Set<TOrganization> organizations = user.getOrganizations();
        List<Tree> tree = new ArrayList<Tree>();
        for (TOrganization torganization : organizations) {
            torganization = organizationService.getById(torganization.getId());
            Set<TUser> users = torganization.getUsers();
            Tree node = new Tree();
            node.setText(torganization.getName());
            node.setId("O_" + torganization.getId());
            node.setPid("O_" + torganization.getPid());
            List<Tree> userTree = new ArrayList<Tree>();
            Map<String, String> attributes = new HashMap<String, String>();
            attributes.put("isuser", "0");
            node.setAttributes(attributes);
            for (TUser tUser : users) {
                if (!"0".equals(tUser.getId())) {
                    Tree userNode = new Tree();
                    userNode.setId(tUser.getId());
                    userNode.setText(tUser.getName());
                    Map<String, String> userAttributes = new HashMap<String, String>();
                    userAttributes.put("isuser", "1");
                    userNode.setAttributes(userAttributes);
                    userTree.add(userNode);
                }
            }
            Collections.sort(userTree, new Comparator<Tree>() {
                @Override
                public int compare(Tree t1, Tree t2) {
                    return t1.getText().compareTo(t2.getText());
                }
            });
            node.setChildren(userTree);
            tree.add(node);
        }
        Collections.sort(tree, new Comparator<Tree>() {
            @Override
            public int compare(Tree t1, Tree t2) {
                return t1.getText().compareTo(t2.getText());
            }
        });

        writeJson(tree);
    }

    /**
     * 重写grid
     */
    public void grid() {
        Grid grid = new Grid();
        String searchOrg = getRequest().getParameter("QUERY_o#id_S_EQ");

        HqlFilter hqlFilter = new HqlFilter(getRequest());
        grid.setTotal(((UserServiceI) service).countByFilter(hqlFilter, searchOrg));
        grid.setRows(((UserServiceI) service).findByFilter(hqlFilter, searchOrg, page, rows));
        writeJson(grid);

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
