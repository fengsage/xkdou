/**
 * www.mbaobao.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.fredzhu.childredhome.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *                       
 * @Filename DateUtil.java
 *
 * @Description 
 *
 * @Version 1.0
 *
 * @Author kuci
 *
 * @Email kuci@mbaobao.com
 *       
 * @History
 *<li>Author: kuci</li>
 *<li>Date: 2012-4-22</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public class DateUtil {

    private final static String FMT_4_URL_CREATETIME = "yyyy-MM-dd HH:mm:ss";

    private final static String FMT_YYYYMMDD         = "yyyy-MM-dd";

    /**
     * 返回指定日期值的文本表示，用“yyyymmdd”格式，如“20121231” :D
     * @param date 要格式化的日期值，不允许null
     */
    public static String formatDateYYYYMMDD(Date d) {
        if (d == null)
            throw new java.lang.IllegalArgumentException("NPE");
        return new SimpleDateFormat(FMT_YYYYMMDD).format(d);
    }

    /**
     * 返回指定日期文本的日期值，使用NormalUrl表的CreateTime字段要求的格式
     * @param dateStr 要解析的日期文本
     * @return 指定日期文本的日期值，如果无法解析则返回null
     */
    public static Date parseDate4UrlCreateTime(String dateStr) {
        Date result = null;
        try {
            result = new SimpleDateFormat(FMT_4_URL_CREATETIME).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Date parseDateYYYYMMDD(String dateStr) {
        Date result = null;
        try {
            result = new SimpleDateFormat(FMT_YYYYMMDD).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 返回指定日期值的文本表示，用NormalUrl表的CreateTime字段要求的格式
     * @param date 要格式化的日期值，不允许null
     */
    public static String formatDate4UrlCreateTime(Date d) {
        if (d == null)
            throw new java.lang.IllegalArgumentException("NPE");
        return new SimpleDateFormat(FMT_4_URL_CREATETIME).format(d);
    }
}
