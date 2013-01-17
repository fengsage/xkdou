/**
 * Copyright (c) 2012 All Rights Reserved.
 */
package com.fredzhu.childredhome.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fredzhu.childredhome.util.PropertiesHelp;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.plugin.activerecord.Record;

/**
 *                       
 * @Filename PermissionChecker.java
 *
 * @Description 
 *
 * @Version 1.0
 *
 * @Author fred
 *
 * @Email me@fengsage.com
 *       
 * @History
 *<li>Author: fred</li>
 *<li>Date: 2012-10-31</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public class PermissionChecker implements Interceptor {

    private static final Logger   LOG                 = Logger.getLogger("auth");

    public static final String    ADMIN_ID            = "ADMIN_ID";
    public static final String    ADMIN_USERNAME      = "ADMIN_USERNAME";
    public static final String    ADMIN_ROLES         = "ADMIN_ROLES";

    public static final String    ADMIN_PERMISSION    = "ADMIN_PERMISSION";

    private static final String   DEV_MODE            = PropertiesHelp.getProperty("dev.mode");

    /**免权限URL*/
    private static final String[] NOT_NEED_CHECK_URLS = new String[] { "/admin/login",
            "/admin/loginSubmit", "/admin/logout"    };

    @SuppressWarnings("unchecked")
    public void intercept(ActionInvocation me) {

        if (!Boolean.parseBoolean(DEV_MODE)) {
            me.invoke();
            return;
        }

        //不用验证权限的
        String requestUrl = me.getActionKey();
        if (!requestUrl.startsWith("/admin")) {
            me.invoke();//允许登录
        }
        for (String url : NOT_NEED_CHECK_URLS) {
            if (url.equals(requestUrl)) {
                me.invoke();
                return;
            }
        }

        //session验证
        HttpSession session = me.getController().getSession();
        String ip = me.getController().getRequest().getRemoteAddr();
        if (session == null || session.getAttribute(ADMIN_ID) == null) {
            LOG.info("未登录用户试图进入:" + requestUrl + ",IP:" + ip);
            me.getController().redirect("/admin/login?redirect=" + requestUrl);
            return;
        }

        //没有验证管理员可用性,暂时不需要
        List<Record> records = ((List<Record>) session.getAttribute(ADMIN_PERMISSION));
        if (records != null && records.size() > 0) {
            for (Record record : records) {
                String permission = record.getStr("permission");
                if (requestUrl.equals(permission.trim())) {
                    me.invoke();//允许登录
                    return;
                }
            }
        }
        try {
            LOG.info("用户权限不够,不能进入:" + requestUrl + ",IP:" + ip);
            HttpServletResponse response = me.getController().getResponse();
            response.setContentType("text/html; charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write("对不起,您没有权限做此项操作!");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            LOG.error("", e);
        }

        me.invoke();//允许登录
    }
}
