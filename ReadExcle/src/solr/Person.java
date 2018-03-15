package solr;

import java.util.Date;
import java.util.UUID;

public class Person {

	private static final long serialVersionUID = 1L;

	// 主键ID
	private String id;
	private Date createdatetime; // 创建时间
	private Date updatedatetime;// 更新时间
	// 语音时间
	// 语音连接
	private String url;
	// 图片连接
	private String pic;
	// 童话名称
	private String name;
	// 童话描述
	private String title;
	// 是否删除，0未删除，1逻辑删除，2物理删除
	private Integer delflag;
	// 播放量
	private Integer num;
	// 播放类型
	private Integer fairType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreatedatetime() {
		return createdatetime;
	}

	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}

	public Date getUpdatedatetime() {
		return updatedatetime;
	}

	public void setUpdatedatetime(Date updatedatetime) {
		this.updatedatetime = updatedatetime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getDelflag() {
		return delflag;
	}

	public void setDelflag(Integer delflag) {
		this.delflag = delflag;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getFairType() {
		return fairType;
	}

	public void setFairType(Integer fairType) {
		this.fairType = fairType;
	}

}
