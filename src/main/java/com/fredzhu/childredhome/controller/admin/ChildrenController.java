/**
 * 
 * Copyright (c) 2012 All Rights Reserved.
 */
package com.fredzhu.childredhome.controller.admin;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.fredzhu.childredhome.controller.BaseController;
import com.fredzhu.childredhome.core.PermissionChecker;
import com.fredzhu.childredhome.core.PermissionOwn;
import com.fredzhu.childredhome.core.VelocityLayoutRender;
import com.fredzhu.childredhome.entity.Children;
import com.fredzhu.childredhome.entity.InfoFromEnum;
import com.fredzhu.childredhome.model.ChildrenModel;
import com.fredzhu.childredhome.model.PermissionModel;
import com.fredzhu.childredhome.util.DateUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 *                       
 * @Filename: ChildrenController.java
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
@PermissionOwn(name = "权限管理")
public class ChildrenController extends BaseController {

    private static final Logger LOG             = Logger.getLogger(ChildrenController.class);

    private ChildrenModel       childrenModel   = new ChildrenModel();
    private PermissionModel     permissionModel = new PermissionModel();

    public void login() {
        setAttr("redirect", getPara("redirect"));
        renderVelocity("login.vm");
    }

    public void loginSubmit() {
        String username = getPara("username");
        String password = getPara("password");
        String redirect = getPara("redirect");

        try {
            if (StringUtils.isEmpty(username)) {
                throw new RuntimeException("用户名不能为空");
            }
            if (StringUtils.isEmpty(password)) {
                throw new RuntimeException("密码不能为空");
            }
            //登录检测
            Record userRecord = Db.findFirst(
                "select * from xkd_auth_user where username = ? and password = ?", username,
                encryptPassword(password));

            if (userRecord == null) {
                throw new RuntimeException("用户名/密码输入错误");
            }

            setSessionAttr(PermissionChecker.ADMIN_ID, userRecord.get("id"));
            setSessionAttr(PermissionChecker.ADMIN_USERNAME, username);
            setSessionAttr(PermissionChecker.ADMIN_ROLES, userRecord.get("role_ids"));

            //保存权限
            setSessionAttr(PermissionChecker.ADMIN_PERMISSION,
                permissionModel.loadUserPermissions(userRecord.getLong("id")));

            if (!StringUtils.isEmpty(redirect)) {
                redirect(redirect);
            } else {
                redirect("/admin/index");
            }

        } catch (Exception e) {
            setAttr("error", e.getMessage());
            renderVelocity("login.vm");
        }
    }

    public void logout() {
        getSession().setAttribute(PermissionChecker.ADMIN_ID, null);
        getSession().setAttribute(PermissionChecker.ADMIN_USERNAME, null);
        redirect("/admin/login");
    }

    @PermissionOwn(name = "后台首页")
    public void index() {
        redirect("/admin/children");
    }

    @PermissionOwn(name = "小蝌蚪管理页面", isMenu = true)
    public void children() {
        setAttr(MENU, "children");
        render(new VelocityLayoutRender("children.vm"));
    }

    @PermissionOwn(name = "小蝌蚪数据查询")
    public void childrenList() {
        int pageNum = getParaToInt("page", 1);
        int rows = getParaToInt("rows", SIZE);

        String realname = getPara("realname", null);
        String whereSql = " where is_del = 0 ";
        if (realname != null) {
            whereSql += " and realname like '%" + realname + "%'";
        }
        Page<Record> page = Db
            .paginate(pageNum, rows, "select *", "from xkd_child_info " + whereSql
                                                 + " order by id desc");
        setAttrs(buildPagination(page.getList(), page.getTotalRow()));
        renderJson();
    }

    @PermissionOwn(name = "更新数据")
    public void childUpdate() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Db.update("xkd_child_info", initChildRecord());
            map.put(RESULT, RESULT_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.warn("出错啦!" + e.getMessage());
            map.put(RESULT, RESULT_FAIL);
            map.put(MESSAGE, e.getMessage());
        }
        renderJson(map);
    }

    @PermissionOwn(name = "删除数据")
    public void childDel() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            int id = getParaToInt("id");
            Db.update("xkd_child_info", "id", new Record().set("id", id).set("is_del", 1));
            map.put(RESULT, RESULT_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.warn("出错啦!" + e.getMessage());
            map.put(RESULT, RESULT_FAIL);
            map.put(MESSAGE, e.getMessage());
        }
        renderJson(map);
    }

    @PermissionOwn(name = "批量更新数据")
    public void childBatchUpdate() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String ids = getPara("ids");
            String data = getPara("data");
            String value = getPara("value");
            for (String id : ids.split(",")) {
                Db.update("xkd_child_info", new Record().set("id", id).set(data, value));
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

    @PermissionOwn(name = "刷新数据")
    public void childRefresh() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String ids = getPara("ids");
            if (StringUtils.isEmpty(ids)) {
                throw new RuntimeException("请选择需要刷新的数据");
            }

            for (String id : ids.split(",")) {
                Record record = Db.findFirst("select * from xkd_child_info where id = ?", id);
                if (record == null) {
                    throw new RuntimeException("原始数据不存在,id=" + id);
                }
                //TODO 不是所有的都可以重新采集 
                InfoFromEnum from = InfoFromEnum.getByCode(record.getStr("info_from"));
                Children children = childrenModel.parseChildren(record.getStr("info_from_url"));
                if (children == null) {
                    throw new RuntimeException("重新采集数据失败,id=" + id + ",数据来源=" + from);
                }
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

    @PermissionOwn(name = "批处理页面", isMenu = true)
    public void batch() {
        setAttr(MENU, "batch");
        render(new VelocityLayoutRender("batch.vm"));
    }

    @PermissionOwn(name = "批处理单URL")
    public void batchParseUrl() {
        final String urls = getPara("urls");
        if (!StringUtils.isEmpty(urls)) {
            new Thread(new Runnable() {
                public void run() {
                    for (String url : urls.split("\\n")) {
                        try {
                            childrenModel.parseChildren(url);
                        } catch (Exception e) {
                            LOG.error("", e);
                        }
                    }
                }
            }).start();
        }
        renderText("ok");
    }

    @PermissionOwn(name = "批处理列表URL")
    public void batchParseListUrl() {
        final String urls = getPara("urls");
        if (!StringUtils.isEmpty(urls)) {
            new Thread(new Runnable() {
                public void run() {
                    for (String url : urls.split("\\n")) {
                        try {
                            childrenModel.parseChildrenList(url);
                        } catch (Exception e) {
                            LOG.error("", e);
                        }
                    }
                }
            }).start();
        }
        renderText("ok");
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
        String type = getPara("type");

        int isShow = getParaToInt("is_show");
        int sex = getParaToInt("sex");
        int isFinded = getParaToInt("is_finded");

        Record record = new Record();
        record.set("id", id);
        record.set("realname", realname);
        record.set("pic", pic);
        record.set("lost_addr", lostAddr);
        record.set("tezheng", tezheng);
        record.set("remark", remark);
        record.set("is_show", isShow);
        record.set("sex", sex);
        record.set("is_finded", isFinded);
        record.set("height", height);
        record.set("type", type);
        record.set("lost_time", DateUtil.parseDate4UrlCreateTime(lostTime));
        record.set("birthday", DateUtil.parseDateYYYYMMDD(birthday));
        return record;
    }
}
