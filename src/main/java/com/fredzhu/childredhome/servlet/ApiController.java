/**
 * Copyright (c) 2012 All Rights Reserved.
 */
package com.fredzhu.childredhome.servlet;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.fredzhu.childredhome.entity.Children;
import com.fredzhu.childredhome.model.ChildrenModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jfinal.core.Controller;

/**
 *                       
 * @Filename: ApiController.java
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
public class ApiController extends Controller {

    private static final Logger LOG  = Logger.getLogger(ApiController.class);

    private static final Gson   gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    public void random() {
        Integer size = getPara("size") != null ? getParaToInt("size") : 1;
        List<Children> list = ChildrenModel.randmon(size <= 10 ? size : 10);
        String jsoncallback = getPara("jsoncallback");
        if (StringUtils.hasText(jsoncallback)) {
            renderText(String.format("%s(%s)", jsoncallback, gson.toJson(list)));
        } else {
            renderText(gson.toJson(list));
        }
    }

    public void parseUrl() {
        try {
            String urls = getPara("url");
            if (StringUtils.hasText(urls)) {
                for (String url : urls.split(",")) {
                    try {
                        ChildrenModel.parseAndSaveURL(url);
                    } catch (Exception e) {
                        LOG.error("", e);
                    }
                }
            }
            renderText("ok");
        } catch (Exception e) {
            renderText(e.getMessage());
        }
    }

    public void batchParseUrl() {
        if ("GET".equals(getRequest().getMethod())) {
            renderVelocity("batchParseUrl.html");
        } else {
            String urls = getPara("urls");
            if (StringUtils.hasText(urls)) {
                for (String url : urls.split("\\n")) {
                    try {
                        ChildrenModel.parseAndSaveURL(url);
                    } catch (Exception e) {
                        LOG.error("", e);
                    }
                }
            }
            renderText("ok");
        }
    }

    public void synUpdate() {
        ChildrenModel.synUpdate();
        renderText("ok");
    }

}
