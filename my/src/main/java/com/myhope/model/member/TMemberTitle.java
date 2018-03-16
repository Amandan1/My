package com.myhope.model.member;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.myhope.model.article.TClassify;

/**
 * 	自选标题
 * */
@Entity
@Table(name = "T_MEMBER_TITLE", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TMemberTitle implements  Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;//id
	
	private Date createdatetime;//创建时间
	
	private TMember member;//所属人
	
	private TClassify titleId;	//标题
	
	private Integer index;//排序
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
		return createdatetime;
	}

	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}
	@ManyToOne
	@JoinColumn(name="c_member_id")
	public TMember getMember() {
		return member;
	}

	public void setMember(TMember member) {
		this.member = member;
	}
	@ManyToOne
	@JoinColumn(name="c_title_id")
	public TClassify getTitleId() {
		return titleId;
	}

	public void setTitleId(TClassify titleId) {
		this.titleId = titleId;
	}
	@Column(name ="c_index",columnDefinition="INT default 0")
	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
	
}
