package com.doginfo.struts.obj;

import com.laodong.pub.product.spring.NllDObj;

/**
 * 表名：doginfo 狗登记信息
 * @author Administrator
 *
 */
public class DoginfoObj extends NllDObj{
    private int id;  //编号
    private String name;  //狗名
    private String master;  //主人名
    private int age;  //狗龄
    private String ms;  //狗的描述
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMaster() {
		return master;
	}
	public void setMaster(String master) {
		this.master = master;
	}
	public String getMs() {
		return ms;
	}
	public void setMs(String ms) {
		this.ms = ms;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
