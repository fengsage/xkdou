/**
 * 
 * Copyright (c) 2012 All Rights Reserved.
 */
package com.fredzhu.childredhome.parse;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fredzhu.childredhome.entity.Children;
import com.fredzhu.childredhome.entity.InfoFromEnum;

/**
 *                       
 * @Filename: BaobeihuijiaParse.java
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
public class BaobeihuijiaParse extends AbstractChildInfoParse {

    private static final Logger LOG          = Logger.getLogger(BaobeihuijiaParse.class);

    private Document            doc          = null;

    private final static String FORMAT_PARAM = "yyyy年MM月dd日";

    private static final String HOST_NAME    = "http://www.baobeihuijia.com";

    private String              url;

    public BaobeihuijiaParse(String url) {
        this.url = url;
    }

    @Override
    public Children parse() {
        try {
            LOG.info(String.format("开始解析[%s],url=%s", InfoFromEnum.BAOBEIHUIJIA.getMessage(), url));
            doc = Jsoup.parse(new URL(url), 20000);

            Children children = convert(doc);
            children.setInfoFromUrl(url);
            children.setPic(getPic(doc));

            return children;
        } catch (Exception e) {
            LOG.error(
                String.format("解析[%s],url=%s,失败", InfoFromEnum.BAOBEIHUIJIA.getMessage(), url), e);
        }
        return null;
    }

    public List<String> parseChildrens() {
        List<String> list = new ArrayList<String>();
        try {
            LOG.info(String.format("开始解析[%s][LIST],url=%s", InfoFromEnum.BAOBEIHUIJIA.getMessage(),
                url));
            doc = Jsoup.parse(new URL(url), 20000);

            Elements eles = doc.select("dt > a");
            for (Element ele : eles) {
                String href = ele.attr("href");
                list.add(HOST_NAME + href);
            }

        } catch (Exception e) {
            LOG.error(String.format("解析[%s][LIST],url=%s,失败",
                InfoFromEnum.BAOBEIHUIJIA.getMessage(), url), e);
        }
        return list;
    }

    //-------------------------内部方法--------------------

    private String getPic(Document doc) {
        String pic = doc.getElementById("_table_1_photo").select("img").attr("src");
        if ("/photo/none-120-150.jpg".equals(pic)) {//空图片
            return "";
        }
        return HOST_NAME + pic;
    }

    private Children convert(Document doc) {
        Children children = new Children();
        children.setInfoFrom(InfoFromEnum.BAOBEIHUIJIA);
        Elements elements = doc.select("div.reginfo").select("ul > li");//注册信息节点

        SimpleDateFormat format = new SimpleDateFormat(FORMAT_PARAM);

        if (elements == null || elements.size() < 12) {
            throw new RuntimeException("解析节点不正确");
        }

        int flag = 0;
        for (Element ele : elements) {
            ele.getElementsByTag("span").remove();//移除span标记
            if (flag == 1) {
                children.setInfoFromNo(ele.getElementsByTag("a").html());
            }
            if (flag == 2) {
                children.setRealname(ele.html());
            }
            if (flag == 3) {
                children.setSex("男".equals(ele.html()) ? 0 : 1);
            }
            if (flag == 4) {
                try {
                    children.setBirthday(format.parse(ele.html()));
                } catch (ParseException e) {
                    LOG.error("", e);
                }
            }
            if (flag == 5) {
                children.setHeight(ele.html());
            }
            if (flag == 6) {
                try {
                    children.setLostTime(format.parse(ele.html()));
                } catch (ParseException e) {
                    LOG.error("", e);
                }
            }
            if (flag == 8) {
                children.setLostAddr(ele.html());
            }
            if (flag == 9) {
                children.setTezheng(ele.html());
            }
            if (flag == 10) {
                children.setRemark(ele.html());
            }
            children.setWeight("");
            flag++;
        }

        return children;
    }
}
