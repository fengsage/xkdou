package com.fredzhu.childredhome.controller;

import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;

/**
 * 
 * Copyright (c) 2012 All Rights Reserved.
 */

/**
 *                       
 * @Filename: IndexController.java
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
 *<li>Date: 2013-1-9</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public class IndexController extends Controller {
	
	@ActionKey("/")
	public void index() {
		renderVelocity("index.html");
	}
	
	@ActionKey("/404")
	public void _404() {
		renderVelocity("404.html");
	}
	
}
