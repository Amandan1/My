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

import com.myhope.model.article.TArticle;
/**
 * 收藏/历史表
 * 
 * */
@Entity
@Table(name = "T_HISTORY", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class THistory implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;//id
	
	private Date createdatetime;//创建时间
	
	private TMember member;//会员
							
	private TArticle articleId;						//文章id
	
	private String type;  //收藏/历史    0/1 
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
	@JoinColumn(name="c_article_id")
	public TArticle getArticleId() {
		return articleId;
	}

	public void setArticleId(TArticle articleId) {
		this.articleId = articleId;
	}
	@Column(name="c_type",length = 2)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
