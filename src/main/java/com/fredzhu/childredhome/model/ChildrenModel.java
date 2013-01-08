/**
 * 
 * Copyright (c) 2012 All Rights Reserved.
 */
package com.fredzhu.childredhome.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.fredzhu.childredhome.entity.Children;
import com.fredzhu.childredhome.entity.InfoFromEnum;
import com.fredzhu.childredhome.parse.ParseFactory;
import com.jfinal.plugin.activerecord.Model;

/**
 *                       
 * @Filename: ChildrenModel.java
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
public class ChildrenModel extends Model<ChildrenModel> {
	
	private static final Logger			LOG					= Logger.getLogger(ChildrenModel.class);
	
	/**
	 *Comment for <code>serialVersionUID</code>
	 */
	private static final long			serialVersionUID	= 2565792245800853119L;
	
	private static final ChildrenModel	dao					= new ChildrenModel();
	
	public static List<Children> randmon(int size) {
		List<Children> list = new ArrayList<Children>();
		
		List<ChildrenModel> records = dao.find(
			"select * from child_info where pic!='' order by rand() limit ?", size <= 10 ? size
				: 10);
		for (ChildrenModel record : records) {
			Children children = new Children();
			children.setBirthday(record.getDate("birthday"));
			children.setHeight(record.getStr("height"));
			children.setInfoFrom(InfoFromEnum.getByCode(record.getStr("info_from")));
			children.setInfoFromNo(record.getStr("info_from_no"));
			children.setInfoFromUrl(record.getStr("info_from_url"));
			children.setLostAddr(record.getStr("lost_addr"));
			children.setLostTime(new Date(record.getTimestamp("lost_time").getTime()));
			children.setPic(record.getStr("pic"));
			children.setRealname(record.getStr("realname"));
			children.setRemark(record.getStr("remark"));
			children.setSex(record.getInt("sex"));
			children.setTezheng(record.getStr("tezheng"));
			children.setWeight(record.getStr("weight"));
			list.add(children);
		}
		return list;
	}
	
	public static Children parseChildren(String url) {
		if (!StringUtils.hasText(url))
			throw new RuntimeException("URL不为空");
		
		Children children = ParseFactory.getInstance().getChildInfoParse(url).parse();
		if (children == null)
			throw new RuntimeException("解析失败!");
		
		ChildrenModel record = dao.findFirst(
			"select * from child_info where info_from = ? and info_from_no = ?", children
				.getInfoFrom().code(), children.getInfoFromNo());
		
		//暂时不考虑更新
		if (record == null) {
			convertToRecord(children).save();
		} else {//更新
			convertToRecord(children).set("id", record.getLong("id")).update();
		}
		return children;
	}
	
	public static List<Children> parseChildrenList(String url) {
		List<Children> list = new ArrayList<Children>();
		
		List<String> links = ParseFactory.getInstance().getChildInfoParse(url).parseChildrens();
		for (String link : links) {
			list.add(parseChildren(link));
		}
		
		return list;
	}
	
	//-------------------------------内部方法---------------------------
	
	private static ChildrenModel convertToRecord(Children children) {
		ChildrenModel record = new ChildrenModel();
		record.set("pic", children.getPic());
		record.set("birthday", children.getBirthday());
		record.set("height", children.getHeight());
		record.set("info_from", children.getInfoFrom().getCode());
		record.set("info_from_no", children.getInfoFromNo());
		record.set("info_from_url", children.getInfoFromUrl());
		record.set("lost_addr", children.getLostAddr());
		record.set("lost_time", children.getLostTime());
		record.set("realname", children.getRealname());
		record.set("remark", children.getRemark());
		record.set("sex", children.getSex());
		record.set("tezheng", children.getTezheng());
		record.set("weight", children.getWeight());
		record.set("create_time", new Date());
		return record;
	}
}
