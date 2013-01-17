/**
 * www.mtwo.com Inc.
 * Copyright (c) 2012 All Rights Reserved.
 */
package com.fredzhu.childredhome.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *                       
 * @Filename PermissionTree.java
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
public class PermissionTree implements Serializable {

    /** Comment for <code>serialVersionUID</code> */
    private static final long    serialVersionUID = -7140006173889323302L;

    private long                 id;

    private String               name;

    private String               alias;

    private long                 parentId;

    private int                  isMenu;

    private String               permission;

    private Date                 addTime;

    private List<PermissionTree> children;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public List<PermissionTree> getChildren() {
        return children;
    }

    public void setChildren(List<PermissionTree> children) {
        this.children = children;
    }

    public int getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(int isMenu) {
        this.isMenu = isMenu;
    }

    @Override
    public String toString() {
        return String
            .format(
                "PermissionTree [id=%s, name=%s, alias=%s, parentId=%s, isMenu=%s, permission=%s, addTime=%s, children=%s]",
                id, name, alias, parentId, isMenu, permission, addTime, children);
    }

}
