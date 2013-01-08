/**
 * 
 * Copyright (c) 2012 All Rights Reserved.
 */
package com.fredzhu.childredhome.parse;

import java.util.List;

import com.fredzhu.childredhome.entity.Children;

/**
 *                       
 * @Filename: ParseBase.java
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
 *<li>Date: 2013-1-4</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public abstract class AbstractChildInfoParse implements Parse {
	
	public abstract Children parse();
	
	public abstract List<String> parseChildrens();
	
}
