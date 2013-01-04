/**
 * 
 * Copyright (c) 2012 All Rights Reserved.
 */
package com.fredzhu.childredhome.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *                       
 * @Filename: InfoFromEnum.java
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
public enum InfoFromEnum {
	
	BAOBEIHUIJIA("BAOBEIHUIJIA", "宝贝回家网");
	
	/** 枚举值 */
	private final String	code;
	
	/** 枚举描述 */
	private final String	message;
	
	/**
	 * 构造一个<code>InfoFromEnum</code>枚举对象
	 *
	 * @param code
	 * @param message
	 */
	private InfoFromEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String code() {
		return code;
	}
	
	/**
	 * @return Returns the message.
	 */
	public String message() {
		return message;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 *
	 * @param code
	 * @return InfoFromEnum
	 */
	public static InfoFromEnum getByCode(String code) {
		for (InfoFromEnum cacheCode : values()) {
			if (cacheCode.getCode().equals(code)) {
				return cacheCode;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<InfoFromEnum>
	 */
	public List<InfoFromEnum> getAllEnum() {
		List<InfoFromEnum> list = new ArrayList<InfoFromEnum>();
		for (InfoFromEnum cache : values()) {
			list.add(cache);
		}
		return list;
	}
	
	/**
	 * 获取全部枚举值
	 * 
	 * @return List<String>
	 */
	public List<String> getAllEnumCode() {
		List<String> list = new ArrayList<String>();
		for (InfoFromEnum cache : values()) {
			list.add(cache.code());
		}
		return list;
	}
}
