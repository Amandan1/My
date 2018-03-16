package com.myhope.model.base;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "T_CONFIG", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TConfig implements java.io.Serializable {
	private static final long serialVersionUID = -1417091520378012420L;

	private String id;
	private Date createdatetime;
	private Date updatedatetime;
	private String createid;
	private String updateid;

	private String key;
	private String name;
	private String val;
	private String type;
	private String remark;

	public TConfig() {
	}

	public TConfig(String id) {
		this.id = id;
	}

	public TConfig(String id, String name, String key, String val, String remark) {
		super();
		this.id = id;
		this.name = name;
		this.key = key;
		this.val = val;
		this.remark = remark;
	}

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
		return this.createdatetime;
	}

	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "c_updatedatetime", length = 7)
	public Date getUpdatedatetime() {
		return this.updatedatetime;
	}

	public void setUpdatedatetime(Date updatedatetime) {
		this.updatedatetime = updatedatetime;
	}

	@Column(name = "c_createid", length = 36)
	public String getCreateid() {
		return createid;
	}

	public void setCreateid(String createid) {
		this.createid = createid;
	}

	@Column(name = "c_updateid", length = 36)
	public String getUpdateid() {
		return updateid;
	}

	public void setUpdateid(String updateid) {
		this.updateid = updateid;
	}

	@Column(name = "c_name", length = 500)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "c_key", length = 500)
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Column(name = "c_val", length = 2000)
	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	@Column(name = "c_type", length = 10)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "c_remark", length = 2000)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
