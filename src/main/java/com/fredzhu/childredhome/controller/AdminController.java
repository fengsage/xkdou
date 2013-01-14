/**
 * 
 * Copyright (c) 2012 All Rights Reserved.
 */
package com.fredzhu.childredhome.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fredzhu.childredhome.util.DateUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 *                       
 * @Filename: AdminController.java
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
 *<li>Date: 2013-1-10</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public class AdminController extends BaseController {
	
	private static final Logger	LOG	= Logger.getLogger(AdminController.class);
	
	public void login() {
		renderVelocity("login.html");
	}
	
	public void loginSubmit() {
		
	}
	
	public void index() {
		redirect("/admin/childrens");
	}
	
	public void children() {
		renderVelocity("childrens.html");
	}
	
	public void childrenList() {
		int pageNum = getParaToInt("page", 1);
		int rows = getParaToInt("rows", SIZE);
		
		String realname = getPara("realname", null);
		String whereSql = " where 1=1 ";
		if (realname != null) {
			whereSql += " and realname like '%" + realname + "%'";
		}
		Page<Record> page = Db.paginate(pageNum, rows, "select *",
			"from child_info " + whereSql + " order by is_show desc, id desc");
		setAttrs(buildPagination(page.getList(), page.getTotalRow()));
		renderJson();
	}
	
	public void childNew() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Db.update("child_info", initChildRecord());
			map.put(RESULT, RESULT_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.warn("出错啦!" + e.getMessage());
			map.put(RESULT, RESULT_FAIL);
			map.put(MESSAGE, e.getMessage());
		}
		renderJson(map);
	}
	
	public void childUpdate() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Db.update("child_info", initChildRecord());
			map.put(RESULT, RESULT_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.warn("出错啦!" + e.getMessage());
			map.put(RESULT, RESULT_FAIL);
			map.put(MESSAGE, e.getMessage());
		}
		renderJson(map);
	}
	
	public void childDel() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int id = getParaToInt("id");
			Db.deleteById("child_info", id);
			map.put(RESULT, RESULT_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.warn("出错啦!" + e.getMessage());
			map.put(RESULT, RESULT_FAIL);
			map.put(MESSAGE, e.getMessage());
		}
		renderJson(map);
	}
	
	public void childShow() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String ids = getPara("ids");
			boolean isShow = getParaToBoolean("is_show", true);
			for (String id : ids.split(",")) {
				Db.update("child_info", new Record().set("id", id).set("is_show", isShow));
			}
			map.put(RESULT, RESULT_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.warn("出错啦!" + e.getMessage());
			map.put(RESULT, RESULT_FAIL);
			map.put(MESSAGE, e.getMessage());
		}
		renderJson(map);
	}
	
	//------------------------内部方法-------------------
	
	private Record initChildRecord() {
		int id = getParaToInt("id");
		String realname = getPara("realname");
		String pic = getPara("pic");
		String lostAddr = getPara("lost_addr");
		String tezheng = getPara("tezheng");
		String remark = getPara("remark");
		String height = getPara("height");
		String lostTime = getPara("lost_time");
		String birthday = getPara("birthday");
		
		int isShow = getParaToInt("is_show");
		int sex = getParaToInt("sex");
		
		Record record = new Record();
		record.set("id", id);
		record.set("realname", realname);
		record.set("pic", pic);
		record.set("lost_addr", lostAddr);
		record.set("tezheng", tezheng);
		record.set("remark", remark);
		record.set("is_show", isShow);
		record.set("sex", sex);
		record.set("height", height);
		record.set("lost_time", DateUtil.parseDate4UrlCreateTime(lostTime));
		record.set("birthday", DateUtil.parseDateYYYYMMDD(birthday));
		return record;
	}
}
