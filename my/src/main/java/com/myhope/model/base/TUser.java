package com.myhope.model.base;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "T_USER", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TUser implements java.io.Serializable {

    private String ip;// 此属性不存数据库，虚拟属性

    private String id;
    private String code;
    private Date createdatetime;
    private Date updatedatetime;
    private String loginname;
    private String pwd;
    private String name;
    private String sex;
    private Integer age;
    private String photo;
    private String qq;
    private String mail;
    private String mobile;
    private Set<TOrganization> organizations = new HashSet<TOrganization>(0);
    private Set<TRole> roles = new HashSet<TRole>(0);

    @Id
    @Column(name = "c_id", unique = true, nullable = false, length = 36)
    public String getId() {
        if (!StringUtils.isBlank(this.id)) {
            return this.id;
        }
        return UUID.randomUUID().toString();
    }

    public void setId(String id) {
        this.id = id;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "c_createdatetime", length = 7)
    public Date getCreatedatetime() {
        if (this.createdatetime != null)
            return this.createdatetime;
        return new Date();
    }

    public void setCreatedatetime(Date createdatetime) {
        this.createdatetime = createdatetime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "c_updatedatetime", length = 7)
    public Date getUpdatedatetime() {
        if (this.updatedatetime != null)
            return this.updatedatetime;
        return new Date();
    }

    public void setUpdatedatetime(Date updatedatetime) {
        this.updatedatetime = updatedatetime;
    }

    @Column(name = "c_loginname", nullable = false, length = 100)
    public String getLoginname() {
        return this.loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    @Column(name = "c_code", nullable = false, length = 20)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "c_pwd", length = 100)
    public String getPwd() {
        return this.pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Column(name = "c_name", length = 100)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "c_mobile" , length = 11)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "c_sex", length = 1)
    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Column(name = "c_age", precision = 8, scale = 0)
    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Column(name = "c_photo", length = 200)
    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Column(name = "c_qq", length = 20)
    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Column(name = "c_mail", length = 50)
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "T_USER_ORGANIZATION", schema = "", joinColumns = {
            @JoinColumn(name = "c_user_id", nullable = false, updatable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "c_organization_id", nullable = false, updatable = false)})
    public Set<TOrganization> getOrganizations() {
        return this.organizations;
    }

    public void setOrganizations(Set<TOrganization> organizations) {
        this.organizations = organizations;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "T_USER_ROLE", schema = "", joinColumns = {
            @JoinColumn(name = "c_user_id", nullable = false, updatable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "c_role_id", nullable = false, updatable = false)})
    public Set<TRole> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<TRole> roles) {
        this.roles = roles;
    }

    @Transient
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}
