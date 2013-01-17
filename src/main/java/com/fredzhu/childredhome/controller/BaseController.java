/**
 * 
 * Copyright (c) 2012 All Rights Reserved.
 */
package com.fredzhu.childredhome.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.fredzhu.childredhome.core.PermissionChecker;
import com.fredzhu.childredhome.util.Util;
import com.jfinal.core.Controller;

/**
 *                       
 * @Filename: BaseController.java
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
 *<li>Date: 2013-1-13</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public class BaseController extends Controller {

    private static final Logger    LOG            = Logger.getLogger(BaseController.class);

    protected static final Integer SIZE           = 20;

    protected static final String  MESSAGE        = "message";
    protected static final String  RESULT         = "result";
    protected static final String  RESULT_SUCCESS = "success";
    protected static final String  RESULT_FAIL    = "fail";

    protected static final String  MENU           = "menu";
    protected static final String  CHILD_MENU     = "child_menu";

    /**
     * 获取管理员ID
     * @return
     */
    protected Integer getAdminID() {
        String adminId = getSession().getAttribute(PermissionChecker.ADMIN_ID).toString();
        return Integer.parseInt(adminId);
    }

    /**
     * 获取管理员名
     * @return
     */
    protected String getAdminName() {
        String username = getSession().getAttribute(PermissionChecker.ADMIN_USERNAME).toString();
        return username;
    }

    @SuppressWarnings("rawtypes")
    protected Map<String, Object> buildPagination(List list, Integer count) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", count);
        map.put("rows", list);
        return map;
    }

    /**
     * 加密密码,返回密文(md(password))
     * @param password 待加密的明文
     * @return
     */
    public String encryptPassword(String password) {
        if (StringUtils.isEmpty(password)) {
            throw new RuntimeException("密码不能为空");
        }
        password = password.trim();
        return Util.MD5Encrypt(password);
    }

    /**
     * 默认生日时间(1970-01-01 00:00:00)
     */
    protected static Date defaultDay() {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return formater.parse("1970-01-01 00:00:00");
        } catch (ParseException e) {
            LOG.error("默认1970日期出错", e);
        }
        throw new RuntimeException("初始化生日1970日期出错");
    }
}
