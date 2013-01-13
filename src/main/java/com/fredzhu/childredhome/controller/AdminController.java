/**
 * 
 * Copyright (c) 2012 All Rights Reserved.
 */
package com.fredzhu.childredhome.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

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
		Page<Record> page = Db.paginate(pageNum, rows, "select *", "from child_info order by id");
		setAttrs(buildPagination(page.getList(), page.getTotalRow()));
		renderJson();
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
		int id = getParaToInt("id");
		Db.deleteById("child_info", id);
	}
	
	//------------------------内部方法-------------------
	
	private Record initChildRecord() {
		int id = getParaToInt("id");
		String realname = getPara("realname");
		String pic = getPara("pic");
		String lost_addr = getPara("lost_addr");
		String tezheng = getPara("tezheng");
		String remark = getPara("remark");
		int is_show = getParaToInt("is_show");
		int sex = getParaToInt("sex");
		
		Record record = new Record();
		record.set("id", id);
		record.set("realname", realname);
		record.set("pic", pic);
		record.set("lost_addr", lost_addr);
		record.set("tezheng", tezheng);
		record.set("remark", remark);
		record.set("is_show", is_show);
		record.set("sex", sex);
		return record;
	}
	
}
