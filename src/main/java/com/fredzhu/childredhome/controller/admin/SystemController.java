/**
 * jiangjia.la Inc.
 * Copyright (c) 2012 All Rights Reserved.
 */
package com.fredzhu.childredhome.controller.admin;

import com.fredzhu.childredhome.controller.BaseController;
import com.fredzhu.childredhome.core.PermissionOwn;
import com.fredzhu.childredhome.core.VelocityLayoutRender;

/**
 *                       
 * @Filename: SystemController.java
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
@PermissionOwn(name = "系统设置", alias = "system")
public class SystemController extends BaseController {

    @PermissionOwn(name = "日志管理页面", isMenu = true)
    public void logs() {
        render(new VelocityLayoutRender("logs.vm"));
    }

}
