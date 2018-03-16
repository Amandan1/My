package com.myhope.model.article;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
/**
 * 文章表
 * */
@Entity
@Table(name = "T_ARTICLE", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)

public class TArticle implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;//id
	
	private Date createDateTime;//创建时间
	
	private TClassify classify;//栏目
	
	private String title;//标题
	
	private String article;//文章
	
	private String img;//大图
	
	private String publisher;//发布人
	
	private String label;//自定义标签
	// 是否删除，0未删除，1逻辑删除
	private Integer delflag;
	
	private Integer shelves;
	
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
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "c_title_id")
	public TClassify getClassify() {
		return classify;
	}

	public void setClassify(TClassify classify) {
		this.classify = classify;
	}
	@Column(name = "c_title",length=2000)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name = "c_article",length=2000)
	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}
	@Column(name = "c_img",length=2000)
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	@Column(name = "c_publisher",length=50)
	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	@Column(name = "c_label",length=36)
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	@Column(name = "c_delflag",length=11)
	public Integer getDelflag() {
		return delflag;
	}

	public void setDelflag(Integer delflag) {
		this.delflag = delflag;
	}
	@Column(name = "c_shelves",length=11)
	public Integer getShelves() {
		return shelves;
	}

	public void setShelves(Integer shelves) {
		this.shelves = shelves;
	}
	
}
