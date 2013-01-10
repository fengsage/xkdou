/**
 * 
 * Copyright (c) 2012 All Rights Reserved.
 */
package com.fredzhu.childredhome.core;

import com.fredzhu.childredhome.controller.ApiController;
import com.fredzhu.childredhome.controller.IndexController;
import com.fredzhu.childredhome.controller.AdminController;
import com.fredzhu.childredhome.controller.ToolsController;
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
		me.add("/tools", ToolsController.class, CoreConfig.TEMPATE_PATH + "/tools");
		me.add("/admin", AdminController.class, CoreConfig.TEMPATE_PATH + "/admin");
	}
	
}
