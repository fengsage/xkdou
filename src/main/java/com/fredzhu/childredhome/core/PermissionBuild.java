/**
 * Copyright (c) 2012 All Rights Reserved.
 */
package com.fredzhu.childredhome.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 *                       
 * @Filename PermissionBuild.java
 *
 * @Description 一个用于快速建立权限的控制器,懒人用的.
 *
 * @Version 1.0
 *
 * @Author fred
 *
 * @Email me@fengsage.com
 *       
 * @History
 *<li>Author: fred</li>
 *<li>Date: 2012-11-12</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public class PermissionBuild implements Interceptor {

    private static final Logger LOG = Logger.getLogger("PermissionBuild");

    @SuppressWarnings({ "rawtypes" })
    public void intercept(ActionInvocation me) {
        try {

            Class controllerClass = me.getController().getClass();
            PermissionOwn controllerOwn = getPermissionOwn(controllerClass);
            PermissionOwn methodOwn = getControllerMethodPermissionOwn(controllerClass,
                me.getMethodName());

            //构建权限的必要添加
            if (controllerOwn != null && methodOwn != null
                && !StringUtils.isEmpty(me.getMethodName())) {
                String controllerName = controllerOwn.name();
                String controllerAlias = controllerOwn.alias();
                if (StringUtils.isEmpty(controllerAlias)) {
                    controllerAlias = controllerClass.getSimpleName();
                    int index = controllerAlias.indexOf(Controller.class.getSimpleName());
                    if (index > 0) {
                        controllerAlias = controllerAlias.substring(0, index).toLowerCase();
                    } else {
                        controllerAlias = controllerClass.getSimpleName();
                    }
                }

                String methodName = methodOwn.name();
                String methodAlias = !StringUtils.isEmpty(methodOwn.alias()) ? methodOwn.alias()
                    : me.getMethodName();//指定alias的则用alias,否则就用用方法名
                String methodParentAlias = methodOwn.parent();
                String permission = me.getController().getRequest().getRequestURI();
                int isMenu = methodOwn.isMenu() ? 1 : 0;
                //                String permission = methodAlias;

                if (!StringUtils.isEmpty(methodParentAlias)) {
                    controllerAlias = methodParentAlias;
                    controllerName = null;
                }

                boolean permIsExist = checkPermissionExist(methodAlias, permission);//使用方法名作为权限alias,使用URL作为权限值
                if (!permIsExist) {//权限不存在,则构建权限
                    createPermission(controllerAlias, controllerName, methodAlias, methodName,
                        permission, isMenu);
                }

            }

        } catch (Exception e) {
            LOG.error("权限构建失败!无法完成权限初始化!", e);
        } finally {
            me.invoke();//不管怎么样,总要继续的,亲
        }

    }

    /**
     * 创建权限 
     */
    private synchronized void createPermission(String controllerAlias, String controllerName,
                                               String methodAlias, String methodName,
                                               String permission, int isMenu) {

        LOG.info(String
            .format(
                "自动创建权限[controllerAlias=%s,controllerName=%s,methodAlias=%s,methodName=%s,permission=%s]",
                controllerAlias, controllerName, methodAlias, methodName, permission));

        if (StringUtils.isEmpty(controllerName)) {//在method指定的parent不存在的时候,默认使用alias作为权限名称
            controllerName = controllerAlias;
        }

        Record controllerRecord = getControllerPermission(controllerAlias);
        if (controllerRecord == null) {
            Db.save(
                "xkd_auth_permission",
                new Record().set("name", controllerName).set("alias", controllerAlias)
                    .set("parent_id", 0).set("permission", "").set("is_menu", "1")
                    .set("add_time", new Date()));
        }

        controllerRecord = getControllerPermission(controllerAlias);
        if (controllerRecord == null) {
            throw new RuntimeException("controller权限不存在,无法建立权限!");
        }

        //建立权限
        Db.save(
            "xkd_auth_permission",
            new Record().set("name", methodName).set("alias", methodAlias)
                .set("parent_id", controllerRecord.get("id")).set("permission", permission)
                .set("is_menu", isMenu).set("add_time", new Date()));

    }

    /**
     * 检查权限是否存在
     */
    private boolean checkPermissionExist(String alias, String permission) {
        if (StringUtils.isEmpty(alias) || StringUtils.isEmpty(permission)) {
            return false;
        }
        Record permRecord = Db.findFirst(
            "select * from xkd_auth_permission where alias = ? and permission = ?", alias,
            permission);
        if (permRecord != null) {
            return true;
        }
        return false;
    }

    private Record getControllerPermission(String controllerAlias) {
        Record controllerRecord = Db
            .findFirst(
                "select * from xkd_auth_permission where alias = ? and permission = ? and parent_id = 0",
                controllerAlias, "");
        return controllerRecord;
    }

    //-------------------------------内部方法-----------------------------------------

    @SuppressWarnings("rawtypes")
    private PermissionOwn getControllerMethodPermissionOwn(Class controllerClass,
                                                           final String methodName) {
        for (Method method : controllerClass.getMethods()) {
            if (methodName.equals(method.getName())) {
                return getPermissionOwn(method);
            }
        }
        return null;
    }

    @SuppressWarnings("rawtypes")
    private PermissionOwn getPermissionOwn(Class cls) {
        Annotation[] annotations = cls.getAnnotations();
        for (Annotation annt : annotations) {
            if (annt instanceof PermissionOwn) {
                PermissionOwn own = (PermissionOwn) annt;
                return own;
            }
        }
        return null;
    }

    private PermissionOwn getPermissionOwn(Method method) {
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annt : annotations) {
            if (annt instanceof PermissionOwn) {
                PermissionOwn own = (PermissionOwn) annt;
                return own;
            }
        }
        return null;
    }

}
