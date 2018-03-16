package com.myhope.model.article;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
/**
 * 栏目表
 * */
@Entity
@Table(name = "T_CLASSIFY", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TClassify implements Serializable{

	 
	private static final long serialVersionUID = 1L;
	
	private String id;//ID
	
	private Date createdatetime;//创建时间
	
	private String title;//标题
	
	private Integer index;//排序
	
	private String depth;//深度
	
	private TClassify parent;//父类
	
	private Set<TArticle> artSet ;
	
	private Integer defaults;
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
	@Column(name = "c_title", length = 50)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name = "c_index", columnDefinition = "INT default 0 ")
	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
	@Column(name = "c_depth",length=10)
	public String getDepth() {
		return depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="c_parent_id")
	public TClassify getParent() {
		return parent;
	}

	public void setParent(TClassify parent) {
		this.parent = parent;
	}
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "classify")
	public Set<TArticle> getArtSet() { 
		return artSet;
	}

	public void setArtSet(Set<TArticle> artSet) {
		this.artSet = artSet;
	}
	@Column(name = "c_default", columnDefinition = "INT default 0 ")
	public Integer getDefaults() {
		return defaults;
	}
	
	public void setDefaults(Integer defaults) {
		this.defaults = defaults;
	}
	
}
