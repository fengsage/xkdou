/**
 * 
 * Copyright (c) 2012 All Rights Reserved.
 */
package com.fredzhu.childredhome.parse;

import com.fredzhu.childredhome.entity.InfoFromEnum;

/**
 *                       
 * @Filename: ParseFactory.java
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
public class ParseFactory {
	
	private static ParseFactory	_instance	= null;
	
	private ParseFactory() {
	}
	
	public static ParseFactory getInstance() {
		if (_instance == null) {
			_instance = new ParseFactory();
		}
		return _instance;
	}
	
	public AbstractChildInfoParse getChildInfoParse(String url) {
		InfoFromEnum infoFrom = checkUrlSite(url);
		switch (infoFrom) {
			case BAOBEIHUIJIA:
				return new BaobeihuijiaParse(url);
		}
		throw new RuntimeException("can't parse url,url=" + url);
	}
	
	private InfoFromEnum checkUrlSite(String url) {
		return InfoFromEnum.BAOBEIHUIJIA;//FIXME 
	}
	
}
