package com.fredzhu.childredhome.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesHelp {
	
	protected static final Logger	LOG				= Logger.getLogger(PropertiesHelp.class);
	
	protected String				fileName		= "/properties/config.properties";
	
	protected static Properties		props			= null;
	private static PropertiesHelp	propertiesHelps	= new PropertiesHelp();
	
	private PropertiesHelp() {
		InputStream in = PropertiesHelp.class.getResourceAsStream(fileName);
		
		props = new Properties();
		try {
			props.load(in);
		} catch (IOException e) {
			LOG.warn("/properties/config.properties读取失败！");
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				LOG.warn("/properties/config.properties关闭失败！");
			}
		}
	}
	
	public static String getProperty(String name) {
		if (propertiesHelps == null) {
			propertiesHelps = new PropertiesHelp();
		}
		return props.getProperty(name);
		
	}
}
