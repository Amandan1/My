package com.myhope.model.base;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "T_METADATA", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TMetadata implements java.io.Serializable {

	private String id;
	private String name;
	private String content;
	private String type;
	private String remark;

	private Set<TMetadataDetail> metadataDetails = new HashSet<TMetadataDetail>(0);

	public TMetadata() {
	}

	public TMetadata(String id) {
		this.id = id;
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

	@Column(name = "c_name", length = 500)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "c_content", length = 2000)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "metadata", cascade = CascadeType.ALL)
	public Set<TMetadataDetail> getMetadataDetails() {
		return metadataDetails;
	}

	public void setMetadataDetails(Set<TMetadataDetail> metadataDetails) {
		this.metadataDetails = metadataDetails;
	}

}
