/**
 * jiangjia.la Inc.
 * Copyright (c) 2012 All Rights Reserved.
 */
package com.fredzhu.childredhome.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.fredzhu.childredhome.controller.BaseController;
import com.fredzhu.childredhome.core.PermissionOwn;
import com.fredzhu.childredhome.core.VelocityLayoutRender;
import com.fredzhu.childredhome.entity.PermissionTree;
import com.fredzhu.childredhome.model.PermissionModel;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 *                       
 * @Filename: AuthController.java
 * @Description: 
 * @Version: 1.0
 * @Author: fred
 * @History:<br>
 *<li>Author: fred</li>
 *<li>Date: 2013-1-17</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
@PermissionOwn(name = "权限管理", alias = "auth")
public class AuthController extends BaseController {

    private static final Logger LOG             = Logger.getLogger(AuthController.class);

    private PermissionModel     permissionModel = new PermissionModel();

    @PermissionOwn(name = "用户管理页面", isMenu = true)
    public void users() {
        setAttr(MENU, "auth");
        setAttr(CHILD_MENU, "users");
        setAttr("roles", permissionModel.findRoles());
        render(new VelocityLayoutRender("users.vm"));
    }

    @PermissionOwn(name = "角色管理页面", isMenu = true)
    public void roles() {
        setAttr(MENU, "auth");
        setAttr(CHILD_MENU, "roles");
        setAttr("firstPerms", permissionModel.findPermissionTree(0L));
        render(new VelocityLayoutRender("roles.vm"));
    }

    @PermissionOwn(name = "权限管理页面", isMenu = true)
    public void perms() {
        setAttr(MENU, "auth");
        setAttr(CHILD_MENU, "perms");
        setAttr("firstPerms", permissionModel.findPermissionChilds(0L));
        render(new VelocityLayoutRender("perms.vm"));
    }

    @PermissionOwn(name = "权限列表数据")
    public void permsData() {
        List<PermissionTree> permissions = permissionModel.findPermissionTree(0L);
        renderJson(permissions);
    }

    @PermissionOwn(name = "创建/更新权限")
    public void permUpdate() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String id = getPara("id");
            Record record = buildPermissionRecord();
            if (StringUtils.isEmpty(id)) {
                record.set("add_time", new Date());
                Db.save(PermissionModel.AUTH_PERMISISON_TABLE, record);
            } else {

                if (permissionModel.findPermissionChilds(Integer.parseInt(id)).size() > 0
                    && getParaToInt("parentId").intValue() != 0) {
                    throw new RuntimeException("树结构不允许超过2层");
                }

                record.set("id", id);
                Db.update(PermissionModel.AUTH_PERMISISON_TABLE, "id", record);
            }

            map.put(RESULT, RESULT_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.warn("出错啦!" + e.getMessage());
            map.put(RESULT, RESULT_FAIL);
            map.put(MESSAGE, e.getMessage());
        }
        setAttrs(map);
        renderJson();
    }

    @PermissionOwn(name = "删除权限")
    public void permDel() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String id = getPara("id");
            if (StringUtils.isEmpty(id)) {
                throw new RuntimeException("ID不能为空");
            }
            Db.deleteById(PermissionModel.AUTH_PERMISISON_TABLE, "id", id);
            map.put(RESULT, RESULT_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.warn("出错啦!" + e.getMessage());
            map.put(RESULT, RESULT_FAIL);
            map.put(MESSAGE, e.getMessage());
        }
        setAttrs(map);
        renderJson();
    }

    @PermissionOwn(name = "角色数据")
    public void rolesData() {
        int pageNum = getParaToInt("page", 1);
        int rows = getParaToInt("rows", 30);
        String name = getPara("name");
        Map<String, String> searchParams = new HashMap<String, String>();
        if (!StringUtils.isEmpty(name)) {
            searchParams.put("name", name);
        }
        Page<Record> page = permissionModel.paginateRoles(pageNum, rows, searchParams);
        setAttrs(buildPagination(page.getList(), page.getTotalRow()));
        renderJson();
    }

    @PermissionOwn(name = "创建/更新角色")
    public void roleUpdate() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String id = getPara("id");
            Record record = buildRoleRecord();
            if (StringUtils.isEmpty(id)) {
                record.set("add_time", new Date());
                Db.save(PermissionModel.AUTH_ROLE_TABLE, record);
            } else {
                record.set("id", id);
                Db.update(PermissionModel.AUTH_ROLE_TABLE, "id", record);
            }
            map.put(RESULT, RESULT_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.warn("出错啦!" + e.getMessage());
            map.put(RESULT, RESULT_FAIL);
            map.put(MESSAGE, e.getMessage());
        }
        setAttrs(map);
        renderJson();
    }

    @PermissionOwn(name = "删除角色")
    public void roleDel() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String id = getPara("id");
            if (StringUtils.isEmpty(id)) {
                throw new RuntimeException("ID不能为空");
            }
            Db.deleteById(PermissionModel.AUTH_ROLE_TABLE, "id", id);
            map.put(RESULT, RESULT_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.warn("出错啦!" + e.getMessage());
            map.put(RESULT, RESULT_FAIL);
            map.put(MESSAGE, e.getMessage());
        }
        setAttrs(map);
        renderJson();
    }

    @PermissionOwn(name = "用户数据")
    public void usersData() {
        int pageNum = getParaToInt("page", 1);
        int rows = getParaToInt("rows", 30);
        String name = getPara("name");
        Map<String, String> searchParams = new HashMap<String, String>();
        if (!StringUtils.isEmpty(name)) {
            searchParams.put("name", name);
        }
        Page<Record> page = permissionModel.paginateUsers(pageNum, rows, searchParams);
        setAttrs(buildPagination(page.getList(), page.getTotalRow()));
        renderJson();
    }

    @PermissionOwn(name = "创建/更新用户")
    public void userUpdate() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String id = getPara("id");
            Record record = buildUserRecord();
            if (StringUtils.isEmpty(id)) {
                record.set("add_time", new Date());
                record.set("last_login_time", defaultDay());
                Db.save(PermissionModel.AUTH_USER_TABLE, record);
            } else {
                record.set("id", id);
                Db.update(PermissionModel.AUTH_USER_TABLE, "id", record);
            }
            map.put(RESULT, RESULT_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.warn("出错啦!" + e.getMessage());
            map.put(RESULT, RESULT_FAIL);
            map.put(MESSAGE, e.getMessage());
        }
        setAttrs(map);
        renderJson();
    }

    @PermissionOwn(name = "删除用户")
    public void userDel() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String id = getPara("id");
            if (StringUtils.isEmpty(id)) {
                throw new RuntimeException("ID不能为空");
            }
            Db.deleteById(PermissionModel.AUTH_USER_TABLE, "id", id);
            map.put(RESULT, RESULT_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.warn("出错啦!" + e.getMessage());
            map.put(RESULT, RESULT_FAIL);
            map.put(MESSAGE, e.getMessage());
        }
        setAttrs(map);
        renderJson();
    }

    @PermissionOwn(name = "我的账户")
    public void myaccount() {
        setAttr("adminName", getAdminName());
        render(new VelocityLayoutRender("myaccounts.html"));
    }

    @PermissionOwn(name = "修改管理员密码")
    public void myaccountModify() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Integer memberId = getAdminID();
            String oldpassword = getPara("oldpassword");
            String password = getPara("password");
            String repassword = getPara("repassword");
            if (!password.equals(repassword)) {
                throw new RuntimeException("重复密码不一致");
            }

            Record record = Db.findFirst("select * from mtwo_auth_user where id = ?", memberId);
            if (record == null) {
                throw new RuntimeException("管理员不存在,id:" + memberId);
            }

            LOG.info(record.getStr("password"));
            LOG.info(encryptPassword(oldpassword));
            LOG.info(oldpassword);

            if (!record.getStr("password").equals(encryptPassword(oldpassword))) {
                throw new RuntimeException("旧密码不正确");
            }

            //重置密码 
            Db.update("update mtwo_auth_user set password = ? where id = ?",
                encryptPassword(password), memberId);

            map.put(RESULT, RESULT_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.warn("出错啦!" + e.getMessage());
            map.put(RESULT, RESULT_FAIL);
            map.put(MESSAGE, e.getMessage());
        }
        setAttrs(map);
        renderJson();
    }

    //-------------------内部方法------------
    private Record buildPermissionRecord() {
        String name = getPara("name");
        String alias = getPara("alias");
        int parentId = getParaToInt("parentId");
        String permission = getPara("permission");
        int isMenu = getParaToInt("isMenu");
        Record record = new Record();
        record.set("name", name);
        record.set("alias", alias);
        record.set("parent_id", parentId);
        record.set("is_menu", isMenu);
        record.set("permission", permission);
        return record;
    }

    private Record buildRoleRecord() {
        String name = getPara("name");
        String[] permissionsIds = getParaValues("perm_ids");

        //父类权限不用选择,有子权限就有父类权限
        permissionsIds = permissionModel.addParentPermssionIds(permissionsIds);

        Record record = new Record();
        record.set("name", name);
        record.set("permissions_ids", convert(permissionsIds));
        record.set("update_time", new Date());
        return record;
    }

    private Record buildUserRecord() {
        String username = getPara("username");
        String password = getPara("password");
        String[] roleIds = getParaValues("role_ids");
        Record record = new Record();
        record.set("username", username);
        if (!StringUtils.isEmpty(password))
            record.set("password", encryptPassword(password));
        record.set("role_ids", convert(roleIds));
        record.set("update_time", new Date());
        return record;
    }

    private String convert(String[] strs) {
        StringBuilder sb = new StringBuilder();
        if (strs == null) {
            return sb.toString();
        }
        for (String str : strs) {
            if (!StringUtils.isEmpty(sb.toString())) {
                sb.append(",");
            }
            sb.append(str);
        }
        return sb.toString();
    }
}
