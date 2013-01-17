/**
 * www.mtwo.com Inc.
 * Copyright (c) 2012 All Rights Reserved.
 */
package com.fredzhu.childredhome.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.fredzhu.childredhome.entity.PermissionTree;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 *                       
 * @Filename PermissionModel.java
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
 *<li>Date: 2012-11-13</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public class PermissionModel {

    public static final String  AUTH_PERMISISON_TABLE = "xkd_auth_permission";
    public static final String  AUTH_ROLE_TABLE       = "xkd_auth_role";
    public static final String  AUTH_USER_TABLE       = "xkd_auth_user";

    private static final Logger LOG                   = Logger.getLogger(PermissionModel.class);

    public List<PermissionTree> findPermissionTree(long parentId) {
        List<PermissionTree> list = findPermissionChilds(parentId);
        if (list == null || list.isEmpty()) {
            return list;
        }
        for (PermissionTree tree : list) {
            tree.setChildren(findPermissionTree(tree.getId()));
        }
        return list;
    }

    public List<PermissionTree> findPermissionChilds(long parentId) {
        List<PermissionTree> treeList = new ArrayList<PermissionTree>();

        List<Record> records = Db.find("select * from xkd_auth_permission where parent_id = ?",
            parentId);

        if (records == null || records.isEmpty()) {
            return treeList;
        }

        for (Record record : records) {
            PermissionTree tree = new PermissionTree();
            tree.setAddTime(record.getTimestamp("add_time"));
            tree.setAlias(record.getStr("alias"));
            tree.setId(record.getLong("id"));
            tree.setName(record.getStr("name"));
            tree.setIsMenu(record.getInt("is_menu"));
            tree.setParentId(record.getLong("parent_id"));
            tree.setPermission(record.getStr("permission"));
            treeList.add(tree);
        }

        return treeList;

    }

    public Page<Record> paginateRoles(int pageNum, int size, Map<String, String> searchParams) {
        pageNum = pageNum > 0 ? pageNum : 1;
        String whereSql = " where 1=1 ";
        if (searchParams.containsKey("name")) {
            whereSql += " and name like '%" + searchParams.get("name") + "%'";
        }
        Page<Record> page = Db.paginate(pageNum, size, "select *", "from xkd_auth_role " + whereSql
                                                                   + " order by add_time desc");
        return page;
    }

    public Page<Record> paginateUsers(int pageNum, int size, Map<String, String> searchParams) {
        pageNum = pageNum > 0 ? pageNum : 1;
        String whereSql = " where 1=1 ";
        if (searchParams.containsKey("name")) {
            whereSql += " and name like '%" + searchParams.get("name") + "%'";
        }
        Page<Record> page = Db.paginate(pageNum, size, "select *", "from xkd_auth_user " + whereSql
                                                                   + " order by add_time desc");

        for (Record user : page.getList()) {
            String roleIds = user.getStr("role_ids");
            if (StringUtils.isEmpty(roleIds)) {
                continue;
            }

            StringBuilder sb = new StringBuilder();
            List<Record> roles = Db.find("select * from xkd_auth_role where id in (" + roleIds
                                         + ")");

            for (Record role : roles) {
                if (!StringUtils.isEmpty(sb.toString())) {
                    sb.append(",");
                }
                sb.append(role.getStr("name"));
            }
            user.set("role_names", sb.toString());
        }
        return page;
    }

    public List<Record> findRoles() {
        return Db.find("select * from xkd_auth_role");
    }

    /**
     * 导出用户权限集合
     * @param userId
     * @return
     */
    public List<Record> loadUserPermissions(long userId) {
        LOG.info("导入用户[" + userId + "]权限");
        long _st = System.currentTimeMillis();

        List<Record> permissionRecords = new ArrayList<Record>();
        Record record = Db.findById("xkd_auth_user", "id", userId);
        if (record != null) {
            String roleIds = record.getStr("role_ids");
            if (StringUtils.isEmpty(roleIds)) {
                return permissionRecords;
            }

            //遍历角色信息
            List<Record> roleRecords = Db.find("select * from xkd_auth_role where id in ("
                                               + roleIds + ")");
            for (Record role : roleRecords) {
                String permissionIds = role.getStr("permissions_ids");
                if (StringUtils.isEmpty(permissionIds)) {
                    continue;
                }
                //查询每个角色下的权限集合
                List<Record> _perms = Db.find("select * from xkd_auth_permission where id in ("
                                              + permissionIds + ")");
                permissionRecords.addAll(_perms);
            }
        }

        LOG.info("导入用户[" + userId + "]权限完成,数据:" + permissionRecords.size() + ",耗时:"
                 + (System.currentTimeMillis() - _st));

        return permissionRecords;
    }

    public String[] addParentPermssionIds(String[] permissionsIds) {
        if (permissionsIds == null) {
            return new String[] {};
        }
        Map<String, String> permissionIds = new HashMap<String, String>();
        for (String pid : permissionsIds) {
            Record record = Db.findFirst("select * from xkd_auth_permission where id = ? limit 1",
                pid);
            if (record != null) {
                permissionIds.put(String.valueOf(record.getLong("id")),
                    String.valueOf(record.getLong("id")));
                long parentId = record.getLong("parent_id");
                if (parentId != 0L) {
                    permissionIds.put(String.valueOf(parentId), String.valueOf(parentId));
                }
            }
        }

        return permissionIds.keySet().toArray(new String[permissionIds.keySet().size()]);
    }
}
