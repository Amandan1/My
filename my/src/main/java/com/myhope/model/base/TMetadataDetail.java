package com.myhope.model.base;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "T_METADATA_DETAIL", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TMetadataDetail implements java.io.Serializable {

	private String id;
	private String key;
	private String val;
	private String remark;
	private Integer seq;

	private TMetadata metadata;

	public TMetadataDetail() {
	}

	public TMetadataDetail(String id) {
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

	@Column(name = "c_remark", length = 2000)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "c_seq", precision = 8, scale = 0)
	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "c_metadata_id")
	public TMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(TMetadata metadata) {
		this.metadata = metadata;
	}

}
