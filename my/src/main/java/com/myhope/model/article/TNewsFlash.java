package com.myhope.model.article;

import java.io.Serializable;
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

/**
 * 快讯
 */
@Entity
@Table(name = "T_NEWS_FLASH", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TNewsFlash implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;  //ID

	private Date createDateTime;//创建时间

	private String title;//标题

	private String article;//内容

	private Integer degree;//重要程度

	private Date sendDate;//发送时间

	private Integer sendStatus;//发送状态
	
	private Integer thumbsNum;//点赞数
	
	private Integer step;//踩数

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
	public Date getCreateDateTime() {
		if (this.createDateTime != null)
			return this.createDateTime;
		return new Date();
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	@Column(name = "c_title", length = 2000)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "c_article", length = 2000)
	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	@Column(name = "c_degree", columnDefinition = "INT default 0 ")
	public Integer getDegree() {
		return degree;
	}

	public void setDegree(Integer degree) {
		this.degree = degree;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "c_send_date", length = 7)
	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	@Column(name = "c_send_status", columnDefinition = "INT default 0 ")
	public Integer getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}
	@Column(name = "c_thumbs_num", columnDefinition = "INT default 0 ")
	public Integer getThumbsNum() {
		return thumbsNum;
	}

	public void setThumbsNum(Integer thumbsNum) {
		this.thumbsNum = thumbsNum;
	}
	@Column(name = "c_step", columnDefinition = "INT default 0 ")
	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

}
