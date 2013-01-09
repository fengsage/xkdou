/**
 * jiangjia.la Inc.
 * Copyright (c) 2012 All Rights Reserved.
 */
package com.fredzhu.childredhome.servlet;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.fredzhu.childredhome.model.ChildrenModel;
import com.jfinal.core.Controller;

/**
 *                       
 * @Filename: ToolsController.java
 * @Description: 
 * @Version: 1.0
 * @Author: fred
 * @History:<br>
 *<li>Author: fred</li>
 *<li>Date: 2013-1-9</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public class ToolsController extends Controller {

    private static final Logger LOG = Logger.getLogger(ToolsController.class);

    public void parseUrl() {
        try {
            String url = getPara("url");
            try {
                ChildrenModel.parseChildren(url);
            } catch (Exception e) {
                LOG.error("", e);
            }
            renderText("ok");
        } catch (Exception e) {
            renderText(e.getMessage());
        }
    }

    public void parseListUrl() {
        try {
            String url = getPara("url");
            try {
                ChildrenModel.parseChildrenList(url);
            } catch (Exception e) {
                LOG.error("", e);
            }
            renderText("ok");
        } catch (Exception e) {
            renderText(e.getMessage());
        }
    }

    public void batch() {
        renderVelocity("batch.html");
    }

    public void batchParseUrl() {
        final String urls = getPara("urls");
        if (StringUtils.hasText(urls)) {
            new Thread(new Runnable() {
                public void run() {
                    for (String url : urls.split("\\n")) {
                        try {
                            ChildrenModel.parseChildren(url);
                        } catch (Exception e) {
                            LOG.error("", e);
                        }
                    }
                }
            }).start();
        }
        renderText("ok");
    }

    public void batchParseListUrl() {
        final String urls = getPara("urls");
        if (StringUtils.hasText(urls)) {
            new Thread(new Runnable() {
                public void run() {
                    for (String url : urls.split("\\n")) {
                        try {
                            ChildrenModel.parseChildrenList(url);
                        } catch (Exception e) {
                            LOG.error("", e);
                        }
                    }
                }
            }).start();
        }
        renderText("ok");
    }
}
