/**
 * 
 * Copyright (c) 2012 All Rights Reserved.
 */
package com.fredzhu.childredhome.entity;

import java.io.Serializable;
import java.util.Date;

import com.fredzhu.childredhome.parse.ParseResult;

/**
 *                       
 * @Filename: Children.java
 *
 * @Description: 
 *
 * @Version: 1.0
 *
 * @Author: fred
 *
 * @Email: me@fengsage.com
 *
 *       
 * @History:<br>
 *<li>Author: fred</li>
 *<li>Date: 2013-1-4</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public class Children implements Serializable, ParseResult {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long	serialVersionUID	= -2031686049295140955L;
	
	private String				realname;
	
	private String				pic;
	
	private InfoFromEnum		infoFrom;
	
	private String				infoFromNo;
	
	private String				infoFromUrl;
	
	private Integer				sex;
	
	private String				birthday;
	
	private String				height;
	
	private String				weight;
	
	private Date				lostTime;
	
	private String				lostAddr;
	
	private String				tezheng;
	
	private String				remark;
	
	public String getRealname() {
		return realname;
	}
	
	public void setRealname(String realname) {
		this.realname = realname;
	}
	
	public InfoFromEnum getInfoFrom() {
		return infoFrom;
	}
	
	public void setInfoFrom(InfoFromEnum infoFrom) {
		this.infoFrom = infoFrom;
	}
	
	public String getInfoFromNo() {
		return infoFromNo;
	}
	
	public void setInfoFromNo(String infoFromNo) {
		this.infoFromNo = infoFromNo;
	}
	
	public Integer getSex() {
		return sex;
	}
	
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
	public String getBirthday() {
		return birthday;
	}
	
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	public String getHeight() {
		return height;
	}
	
	public void setHeight(String height) {
		this.height = height;
	}
	
	public String getWeight() {
		return weight;
	}
	
	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	public Date getLostTime() {
		return lostTime;
	}
	
	public void setLostTime(Date lostTime) {
		this.lostTime = lostTime;
	}
	
	public String getLostAddr() {
		return lostAddr;
	}
	
	public void setLostAddr(String lostAddr) {
		this.lostAddr = lostAddr;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getInfoFromUrl() {
		return infoFromUrl;
	}
	
	public void setInfoFromUrl(String infoFromUrl) {
		this.infoFromUrl = infoFromUrl;
	}
	
	public String getTezheng() {
		return tezheng;
	}
	
	public void setTezheng(String tezheng) {
		this.tezheng = tezheng;
	}
	
	public String getPic() {
		return pic;
	}
	
	public void setPic(String pic) {
		this.pic = pic;
	}
	
	@Override
	public String toString() {
		return String
			.format(
				"Children [realname=%s, pic=%s, infoFrom=%s, infoFromNo=%s, infoFromUrl=%s, sex=%s, birthday=%s, height=%s, weight=%s, lostTime=%s, lostAddr=%s, tezheng=%s, remark=%s]",
				realname, pic, infoFrom, infoFromNo, infoFromUrl, sex, birthday, height, weight,
				lostTime, lostAddr, tezheng, remark);
	}
	
}
