/**
 * Copyright (c) 2012 All Rights Reserved.
 */
package com.fredzhu.childredhome.servlet;

import java.util.Date;
import java.util.List;

import org.mortbay.log.Log;
import org.springframework.util.StringUtils;

import com.fredzhu.childredhome.entity.Children;
import com.fredzhu.childredhome.parse.ParseFactory;
import com.google.gson.Gson;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 *                       
 * @Filename: ApiController.java
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
public class ApiController extends Controller {
	
	private static final Gson	gson	= new Gson();
	
	public void index() {
		renderText("此方法是一个action");
	}
	
	public void random() {
		Integer size = getPara("size") != null ? getParaToInt("size") : 1;
		List<Record> records = Db.find(
			"select * from child_info where pic!='' order by rand() limit ?", size <= 10 ? size
				: 10);
		String jsoncallback = getPara("jsoncallback");
		if (StringUtils.hasText(jsoncallback)) {
			renderText(String.format("%s(%s)", jsoncallback, gson.toJson(records)));
		} else {
			renderText(gson.toJson(records));
		}
	}
	
	public void parseUrl() {
		String url = getPara("url");
		if (StringUtils.hasText(url)) {
			Children children = ParseFactory.getInstance().getChildrenInfo(url);
			if (children == null) {
				renderText("parse fail");
				return;
			}
			
			Record record = Db.findFirst(
				"select * from child_info where info_from = ? and info_from_no = ?", children
					.getInfoFrom().code(), children.getInfoFromNo());
			
			//暂时不考虑更新
			if (record == null) {
				record = new Record();
				record.set("pic", children.getPic());
				record.set("birthday", children.getBirthday());
				record.set("height", children.getHeight());
				record.set("info_from", children.getInfoFrom().getCode());
				record.set("info_from_no", children.getInfoFromNo());
				record.set("info_from_url", children.getInfoFromUrl());
				record.set("lost_addr", children.getLostAddr());
				record.set("loat_time", children.getLostTime());
				record.set("realname", children.getRealname());
				record.set("remark", children.getRemark());
				record.set("sex", children.getSex());
				record.set("tezheng", children.getTezheng());
				record.set("weight", children.getWeight());
				record.set("create_time", new Date());
				Db.save("child_info", record);
				Log.info("保存儿童数据完毕,record=" + record);
			}
			
			renderText(gson.toJson(children));
			return;
		}
		renderText("please input url");
	}
	
}
