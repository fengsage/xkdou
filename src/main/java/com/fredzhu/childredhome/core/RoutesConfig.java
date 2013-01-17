/**
 * 
 * Copyright (c) 2012 All Rights Reserved.
 */
package com.fredzhu.childredhome.core;

import com.fredzhu.childredhome.controller.ApiController;
import com.fredzhu.childredhome.controller.IndexController;
import com.fredzhu.childredhome.controller.admin.AuthController;
import com.fredzhu.childredhome.controller.admin.ChildrenController;
import com.fredzhu.childredhome.controller.admin.SystemController;
import com.jfinal.config.Routes;

/**
 *                       
 * @Filename: Routes.java
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
public class RoutesConfig {

    public void configRoute(Routes me) {
        me.add("/", IndexController.class, CoreConfig.TEMPATE_PATH);
        me.add("/api", ApiController.class, CoreConfig.TEMPATE_PATH + "/api");
        me.add("/admin", ChildrenController.class, CoreConfig.TEMPATE_PATH + "/admin");
        me.add("/admin/auth", AuthController.class, CoreConfig.TEMPATE_PATH + "/admin/auth");
        me.add("/admin/system", SystemController.class, CoreConfig.TEMPATE_PATH + "/admin/system");
    }

}
