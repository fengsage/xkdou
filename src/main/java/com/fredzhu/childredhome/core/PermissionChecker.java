/**
 * Copyright (c) 2012 All Rights Reserved.
 */
package com.fredzhu.childredhome.core;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

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

    /**免权限URL*/
    private static final String[] NOT_NEED_CHECK_URLS = new String[] { "/admin/login",
            "/admin/loginSubmit", "/admin/logout"    };

    public void intercept(ActionInvocation me) {

        String requestUrl = me.getActionKey();

        //不用验证权限的
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
        me.invoke();//允许登录
    }
}
