package com.fredzhu.childredhome.util;

import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 工具函数集合
 * @author guosong
 */
public class Util {
    protected static final Logger log           = LogManager.getLogger(Util.class);

    protected static String[]     WEEK_NAME     = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };

    public static String          MSG_SEPARATOR = "#";

    public static BigDecimal max(BigDecimal source, BigDecimal target) {
        if (source == null || target == null) {
            return BigDecimal.ZERO;
        }
        return source.compareTo(target) < 0 ? target : source;
    }

    public static BigDecimal min(BigDecimal source, BigDecimal target) {
        if (source == null || target == null) {
            return BigDecimal.ZERO;
        }
        return source.compareTo(target) < 0 ? source : target;
    }

    public static String getRandomUUID() {
        String temp = UUID.randomUUID().toString();
        temp = temp.replaceAll("-", "");
        return temp;
    }

    /**
     * 字符串拼接（split相反）
     * @param separator
     * @param list
     * @return
     */
    public static String implode(String separator, List<String> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        Integer num = list.size();
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < num; i++) {
            sb.append(list.get(i));
            if (i < num - 1) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    public static boolean isContains(String value, Integer id) {
        if (StringUtils.isEmpty(value) || id == null) {
            return false;
        }
        String[] keys = value.split(",");
        if (keys == null || keys.length <= 0) {
            return false;
        }
        for (int i = 0; i < keys.length; i++) {
            if (keys[i].equalsIgnoreCase(String.valueOf(id))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isContains(String value, String key) {
        if (StringUtils.isBlank(value) || StringUtils.isBlank(key)) {
            return false;
        }
        String[] keys = value.split(",");
        if (keys == null || keys.length <= 0) {
            return false;
        }
        for (int i = 0; i < keys.length; i++) {
            if (StringUtils.trim(keys[i]).equalsIgnoreCase(StringUtils.trim(key))) {
                return true;
            }
        }
        return false;
    }

    public static String mergeString(String one, String two) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isBlank(one) || StringUtils.isBlank(two)) {
            return sb.toString();
        }
        String[] oneKeys = one.split(",");
        String[] twoKeys = two.split(",");
        for (int i = 0; i < oneKeys.length; i++) {
            for (int j = 0; j < twoKeys.length; j++) {
                if (oneKeys[i].equalsIgnoreCase(twoKeys[j])) {
                    sb.append(oneKeys[i]);
                    break;
                }
            }
        }
        return sb.toString();
    }

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
     * 返回指定日期值的文本表示，用NormalUrl表的CreateTime字段要求的格式
     * @param date 要格式化的日期值，不允许null
     */
    public static String formatDate4UrlCreateTime(Date d) {
        if (d == null)
            throw new java.lang.IllegalArgumentException("NPE");

        return new SimpleDateFormat(FMT_4_URL_CREATETIME).format(d);
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
            log.error("错误的日期值 [" + dateStr + "]", e);
        }

        return result;
    }

    public static int getPid() {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        String name = runtime.getName(); // format: "pid@hostname"   
        try {
            return Integer.parseInt(name.substring(0, name.indexOf('@')));
        } catch (Exception e) {
            return -1;
        }
    }

    //    public static void main(String[] args) {
    //        //            System.out.println(isContains("1208026101,1208026001,1208026201,1208026301,1208026401",
    //        //                1208026401));
    //        System.out.println(getRandomUUID());
    //        //System.out.println(StringEscapeUtils.escapeHtml4("<\">?中文"));
    //        //System.out.println(StringEscapeUtils.escapeXml("<\">?中文"));
    //    }

    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str.trim())) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isWindows() {
        Properties prop = System.getProperties();
        String os = prop.getProperty("os.name");
        if (os.startsWith("win") || os.startsWith("Win")) {
            return true;
        } else {
            return false;
        }
    }

    /**
    * 生成随机密码
    * @param passLenth 生成的密码长度
    * @return 随机密码
    */
    public static String getRandomPass() {

        //没有字母O的大小写
        StringBuffer buffer = new StringBuffer(
            "0123456789abcdefghijklmnpqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ");
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        int range = buffer.length();
        for (int i = 0; i < 8; i++) {
            //生成指定范围类的随机数0—字符串长度(包括0、不包括字符串长度)
            sb.append(buffer.charAt(r.nextInt(range)));
        }
        return sb.toString();
    }

    /**
     * 
     * @param text 目标字符串
     * @param length 截取长度
     * @param encode 采用的编码方式
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String substring(String text, int length) {
        if (text == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int currentLength = 0;
        for (char c : text.toCharArray()) {
            try {
                currentLength += String.valueOf(c).getBytes("UTF-8").length;
            } catch (Exception e) {
                log.error("字符转码失败！", e);
                continue;
            }
            if (currentLength <= length) {
                sb.append(c);
            } else {
                break;
            }
        }
        return sb.toString();
    }

    /*
     * String.valueOf(arr[i]).matches("[\u4e00-\u9fa5]") 只是汉字
     * String.valueOf(arr[i]).matches("[^x00-xff]") 双字节（包括汉字）
     */
    //    public static String getStrBylength(String str, int len) {
    //        if (null == str)
    //            return "";
    //        int sl = str.getBytes().length;
    //        if (sl > len * 2) {
    //            StringBuffer sb = new StringBuffer();
    //            char[] arr = str.toCharArray();
    //            for (int i = 0, j = 0; i < arr.length && j < len * 2; i++) {
    //                if (String.valueOf(arr[i]).matches("[^x00-xff]")) {
    //                    j += 2;
    //                } else {
    //                    j++;
    //                }
    //                if (j == len * 2 - 1 && String.valueOf(arr[i]).matches("[^x00-xff]")) {
    //
    //                } else {
    //                    sb.append(arr[i]);
    //                }
    //            }
    //            return sb.toString() + "...";
    //        } else {
    //            return str;
    //        }
    //    }

    /**  
         * 此方法描述的是：取指定长度字符串的值;  
         * @param String str,int byteSize;    
         * @return String;  
         */

    public String getAllStringSize(String str, int byteSize) {
        int len = 0;
        char c;
        String words = "";

        if (str == null || "null".equals(str)) {
            return "";
        }

        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                // 字母, 数字   
                len++;
            } else {
                if (String.valueOf(c).matches("[\\u4E00-\\u9FA5]")) { // 中文   
                    len += 2;
                } else { // 符号或控制字符   
                    len++;
                }
            }
            words += String.valueOf(c);
            if (len >= byteSize) {//   
                words += "..";
                break;
            }
        }
        return words;
    }

    //该方法还处理不了中文符号！，。等
    public static boolean isOverLength(String str, int length) {
        int len = 0;
        char c;

        if (str == null || "null".equals(str)) {
            return true;
        }

        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                // 字母, 数字   
                len++;
            } else {
                if (String.valueOf(c).matches("[\\u4E00-\\u9FA5]")) { // 中文   
                    len += 2;
                } else { // 符号或控制字符   
                    len++;
                }
            }

            if (len > length) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidIP(String ip) {
        if (isEmpty(ip))
            return false;
        Pattern pattern = Pattern
            .compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
        Matcher matcher = pattern.matcher(ip); //以验证127.400.600.2为例   
        return matcher.matches();
    }

    public static String MD5Encrypt(String str) {
        String s = str;
        if (s == null) {
            return null;
        }
        String value = null;
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            log.error("没有此类加密算法", ex);
            //Logger.getLogger(Util.class.getName()).log(Level.ERROR, "没有此类加密算法", ex);
        }
        sun.misc.BASE64Encoder baseEncoder = new sun.misc.BASE64Encoder();
        try {
            value = baseEncoder.encode(md5.digest(s.getBytes("utf-8")));
        } catch (Exception ex) {
            log.error("加密算法执行失败", ex);
        }
        return value;
    }

    public static String zeroFormat(Integer number) {
        String pattern = "000000";
        java.text.DecimalFormat df = new java.text.DecimalFormat(pattern);
        return df.format(number);
    }

    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * Email地址格式检查
     * 
     * @param email
     * @return
     */
    public static boolean isMail(String email) {
        boolean flag = false;

        try {
            if (email == null) {
                return flag;
            }
            email = email.replaceAll(" ", "");
            if ("".equals(email)) {
                return flag;
            }
            Pattern pattern = Pattern
                .compile("^[\\w\\.\\-]{1,}[@][\\w\\-]{1,}([.]([\\w\\-]{2,})){1,3}$");
            Matcher mat = pattern.matcher(email);

            if (mat.find()) {
                flag = true;
            }
        } catch (Exception e) {
            return false;
        }
        return flag;
    }

    /**
     * 获取允许的星期名
     * @param allowWeek (格式：1,2,3,4,5,6,0)
     * @return 返回格式：周一,周二
     */
    public static String getAllowWeek(String allowWeek) {
        String names = "";

        if (StringUtils.isEmpty(allowWeek)) {
            return names;
        }

        int length = WEEK_NAME.length;
        String[] tokens = allowWeek.split(",");

        for (String id : tokens) {
            if (StringUtils.isEmpty(id) || !StringUtils.isNumeric(id)) {
                continue;
            }

            int key = Integer.parseInt(id);

            if (key < length) {
                names += WEEK_NAME[key] + ",";
            }
        }

        if (StringUtils.isNotEmpty(names)) {
            names = names.substring(0, names.length() - 1);
        }

        return names;
    }

    /**
     * 转换成数组
     * @param input
     * @param delimiter
     * @return
     */
    public static List<String> split(String input, char delimiter) {
        if (StringUtils.isEmpty(input)) {
            return new ArrayList<String>(0);
        }

        String[] tokens = input.split(delimiter + "");
        List<String> result = new ArrayList<String>(tokens.length);
        for (String s : tokens) {
            result.add(StringUtils.trim(s));
        }
        return result;
    }

    public static Integer getFirstValue(String source) {
        return Integer.valueOf(getFirstValue(source, ','));
    }

    public static String getFirstValue(String source, char delimiter) {
        List<String> values = split(source, delimiter);
        if (values.size() > 0) {
            return values.get(0);
        } else {
            return "";
        }
    }

    /**
     * 设置业务错误信息
     * @param newMsg 新的错误信息
     * @param currentMsg 当前的错误信息
     * @return
     */
    public static String setErrorMsg(String newMsg, String currentMsg) {
        if (currentMsg == null) {
            currentMsg = "";
        }

        if (StringUtils.isEmpty(newMsg)) {
            return currentMsg;
        }

        // 按分割符，将最新的错误信息保存，并保存在最前
        if (StringUtils.isNotEmpty(currentMsg)) {
            currentMsg = newMsg + MSG_SEPARATOR + currentMsg;
        } else {
            currentMsg = newMsg;
        }
        return StringUtils.abbreviate(currentMsg, 500);
    }

    public static String getLocalIP() {
        String ip = "";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e1) {
            log.warn("获取本机IP失败！", e1);
        }
        return ip;
    }

    /**
     * @return 本机的第一个IP地址（IPV4，非localhost和组播地址）
     */
    public static String getFirstLocalIP() {
        String result = null;

        //根据网卡取本机配置的IP     
        Enumeration<NetworkInterface> netInterfaces;

        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (result == null && netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                //              System.out.println(ni.getName());

                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (result == null && ips.hasMoreElements()) {
                    ip = ips.nextElement();
                    //                  System.out.println(ip);
                    if (ip instanceof Inet4Address && !ip.isLoopbackAddress()
                        && !ip.isMulticastAddress()) {
                        //                      System.out.println("本机的ip=" + ip.getHostAddress());
                        result = ip.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        //return result == null ? "[n/a] " : "[" + result + "] ";
        return result == null ? "" : result;
    }

    /**
     * 校验时间是否有效
     * @param stime
     * @param etime
     * @param now
     * @return
     */
    public static boolean isEffectiveTime(Object stime, Object etime, long now) {
        String stimeStr = stime != null ? stime.toString() : "";
        String etimeStr = etime != null ? etime.toString() : "";

        if (StringUtils.isEmpty(stimeStr) || StringUtils.isEmpty(etimeStr)) {
            return false;
        }

        try {
            DateFormat df = new SimpleDateFormat(FMT_4_URL_CREATETIME);
            long startTime = df.parse(stimeStr).getTime();
            long endTime = df.parse(etimeStr).getTime();
            if (now >= startTime && now <= endTime) {
                return true;
            }
        } catch (Exception e) {
            log.warn("时间区间转换失败,stime=" + stime + ";etime=" + etime, e);
            return false;
        }

        return false;
    }

    public static String getMixedString(String content, int type) {
        //1姓名 2电话 3地址
        String result = "";
        if (StringUtils.isBlank(content) || type <= 0) {
            return result;
        }
        if (type == 1) {
            result = StringUtils.overlay(content, "*", 0, 1);
        } else if (type == 2) {
            if (content.length() >= 1 && content.length() <= 5) {
                result = StringUtils.overlay(content, "****", 1, content.length() - 1);
            } else if (content.length() > 5 && content.length() <= 8) {
                result = StringUtils.overlay(content, "****", 3, 6);
                //content.
            } else if (content.length() > 8 && content.length() <= 12) {
                result = StringUtils.overlay(content, "****", 3, 7);
            } else {
                result = StringUtils.overlay(content, "**********", 3, content.length() - 1);
            }
        } else if (type == 3) {
            if (content.length() >= 1 && content.length() <= 5) {
                result = StringUtils.overlay(content, "****", 1, content.length() - 1);
            } else if (content.length() > 5 && content.length() <= 8) {
                result = StringUtils.overlay(content, "****", 3, 6);
                //content.
            } else if (content.length() > 8 && content.length() <= 12) {
                result = StringUtils.overlay(content, "****", 3, 7);
            } else {
                result = StringUtils.overlay(content, "**********", 3, content.length() - 1);
            }
        } else {
            result = StringUtils.overlay(content, "**********", 3, content.length() - 1);
        }
        return result;
    }

    /**
     * 判断一个字符是Ascill字符还是其它字符（如汉，日，韩文字符）
     * 
     * @param char
     *            c, 需要判断的字符
     * @return boolean, 返回true,Ascill字符
     */
    public static boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }

    /**
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
     * 
     * @param String
     *            s ,需要得到长度的字符串
     * @return int, 得到的字符串长度
     */
    public static int length(String s) {
        if (s == null)
            return 0;
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!isLetter(c[i])) {
                len++;
            }
        }
        return len;
    }

    /**
     * 截取一段字符的长度,不区分中英文,如果数字不正好，则少取一个字符位
     * 
     * @author patriotlml
     * @param String
     *            origin, 原始字符串
     * @param int
     *            len, 截取长度(一个汉字长度按2算的)
     * @return String, 返回的字符串
     */
    public static String substringOfChar(String origin, int len) {
        if (origin == null || origin.equals("") || len < 1)
            return "";
        byte[] strByte = new byte[len];
        if (len > Util.length(origin)) {
            return origin;
        }
        System.arraycopy(origin.getBytes(), 0, strByte, 0, len);
        int count = 0;
        for (int i = 0; i < len; i++) {
            int value = (int) strByte[i];
            if (value < 0) {
                count++;
            }
        }
        if (count % 2 != 0) {
            len = (len == 1) ? ++len : --len;
        }
        return new String(strByte, 0, len);
    }

    public static String subStringChar(String sourceString, int maxLength) {
        String resultString = "";
        if (sourceString == null || sourceString.equals("") || maxLength < 1) {
            return resultString;
        } else if (sourceString.length() <= maxLength) {
            return sourceString;
        } else if (sourceString.length() > 2 * maxLength) {
            sourceString = sourceString.substring(0, 2 * maxLength);
        }

        if (sourceString.length() > maxLength) {
            char[] chr = sourceString.toCharArray();
            int strNum = 0;
            int strGBKNum = 0;
            boolean isHaveDot = false;

            for (int i = 0; i < sourceString.length(); i++) {
                if (chr[i] >= 0xa1) //0xa1汉字最小位开始
                {
                    strNum = strNum + 2;
                    strGBKNum++;
                } else {
                    strNum++;
                }

                if (strNum == 2 * maxLength || strNum == 2 * maxLength + 1) {
                    if (i + 1 < sourceString.length()) {
                        isHaveDot = true;
                    }
                    break;
                }
            }
            resultString = sourceString.substring(0, strNum - strGBKNum);
            if (!isHaveDot) {
                resultString = resultString + "...";
            }
        }

        return resultString;
    }

    /**
     * 过滤掉html标签
     * @param inputString
     * @return
     */
    public static String getHtmlText(String inputString) {
        String htmlStr = inputString; //含html标签的字符串 
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;
        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> } 
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> } 
            String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式 

            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); //过滤script标签 

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); //过滤style标签 

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); //过滤html标签 

            /* 空格 ——   */
            // p_html = Pattern.compile("\\ ", Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = htmlStr.replaceAll(" ", " ");

            textStr = htmlStr;

        } catch (Exception e) {
        }
        return textStr;
    }

    // 过滤特殊字符  
    public static String stringFilter(String str) {
        // 只允许字母和数字       
        // String   regEx  =  "[^a-zA-Z0-9]";                     
        // 清除掉所有特殊字符  
        String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    //    private final static SimpleDateFormat SDF                  = new SimpleDateFormat(
    //                                                                   "yyyy-MM-dd HH:mm:ss");
    private final static String FMT_4_URL_CREATETIME = "yyyy-MM-dd HH:mm:ss";
    private final static String FMT_YYYYMMDD         = "yyyyMMdd";
    //public final static SimpleDateFormat FMT_4_URL_CREATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //public final static SimpleDateFormat FMT_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
}
