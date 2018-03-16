package com.myhope.model.article;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.myhope.model.member.TMember;

@Entity
@Table(name = "T_THUMBS_LOG", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TThumbsLog implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String id;//ID
	
	private TMember member;//会员
	
	private TNewsFlash news;//快讯
	
	private Integer type;//赞/踩
	
	private Date createDateTime;//创建时间

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
	@OneToOne
	@JoinColumn(name="c_member_id")
	public TMember getMember() {
		return member;
	}

	public void setMember(TMember member) {
		this.member = member;
	}
	@OneToOne
	@JoinColumn(name="c_news_id")
	public TNewsFlash getNews() {
		return news;
	}

	public void setNews(TNewsFlash news) {
		this.news = news;
	}
	@Column(name = "c_type", columnDefinition = "INT default 0 ")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "c_createdatetime", length = 7)
	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}
	
	
	
}
