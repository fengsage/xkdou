/**
 * 
 * Copyright (c) 2012 All Rights Reserved.
 */
package com.fredzhu.childredhome.controller;

import com.jfinal.core.Controller;

/**
 *                       
 * @Filename: AdminController.java
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
public class AdminController extends Controller {
	
	public void login() {
		renderVelocity("login.html");
	}
	
	public void loginSubmit() {
		
	}
	
	public void index() {
		renderVelocity("index.html");
	}
	
}
