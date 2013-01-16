/**
 * www.mtwo.com Inc.
 * Copyright (c) 2012 All Rights Reserved.
 */
package com.fredzhu.childredhome.core;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import com.jfinal.core.JFinal;
import com.jfinal.log.Logger;
import com.jfinal.render.Render;
import com.jfinal.render.RenderException;

/**
 *                       
 * @Filename VelocityLayoutRender.java
 *
 * @Description 
 *
 * @Version 1.0
 *
 * @Author fred
 *
 * @Email me@fengsage.com
 *       
 * @History
 *<li>Author: fred</li>
 *<li>Date: 2012-11-4</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public class VelocityLayoutRender extends Render {

    private static final Logger               LOG                          = Logger
                                                                               .getLogger(VelocityLayoutRender.class);

    /** Comment for <code>serialVersionUID</code> */
    private static final long                 serialVersionUID             = -3869666885017754893L;

    private static final String               VELOCITY_PROPERTY_FILE       = "/velocity.properties";

    private transient static final String     encoding                     = getEncoding();

    private transient static final String     contentType                  = "text/html;charset="
                                                                             + encoding;
    private transient static final Properties properties                   = new Properties();

    private static final String               PROPERTY_WEBAPP_LOADER_CLASS = "webapp.resource.loader.class";
    private static final String               PROPERTY_WEBAPP_LOADER_PATH  = "webapp.resource.loader.path";

    private static final String               PROPERTY_ERROR_TEMPLATE      = "tools.view.servlet.error.template";
    private static final String               PROPERTY_LAYOUT_DIR          = "tools.view.servlet.layout.directory";
    private static final String               PROPERTY_DEFAULT_LAYOUT      = "tools.view.servlet.layout.default.template";
    private static final String               PROPERTY_TOOLS_BOX           = "tools.toolbox";

    public static final String                SERVLET_CONTEXT_KEY          = ServletContext.class
                                                                               .getName();

    private static final String               KEY_SCREEN_CONTENT           = "screen_content";
    private static final String               DEFAULT_DEFAULT_LAYOUT       = "layout.html";
    private static final String               DEFAULT_ERROR                = "error.html";
    private static final String               DEFAULT_LAYOUT_DIR           = "/WEB-INF/templates/layout/";

    private static String                     layout                       = null;
    private static VelocityEngine             ve                           = null;

    //    @SuppressWarnings("deprecation")
    //    private static ToolboxManager             toolboxManager               = null;
    //    private static String                     toolbox                      = null;

    public VelocityLayoutRender(String view) {
        this.view = view;
    }

    private static Properties loadVelocityProperties() {
        Properties fileProerties = new Properties();
        try {
            fileProerties.load(VelocityLayoutRender.class
                .getResourceAsStream(VELOCITY_PROPERTY_FILE));
        } catch (IOException e) {
            LOG.warn("velocity.properties not exist");
        }
        return fileProerties;
    }

    private static void initVelovityProperties() {
        /**编码配置*/
        properties.setProperty(Velocity.ENCODING_DEFAULT, encoding);
        properties.setProperty(Velocity.INPUT_ENCODING, encoding);
        properties.setProperty(Velocity.OUTPUT_ENCODING, encoding);

        /**模板webapp路径*/
        properties.setProperty(Velocity.RESOURCE_LOADER, "webapp");
        properties.setProperty(PROPERTY_WEBAPP_LOADER_CLASS,
            "org.apache.velocity.tools.view.WebappResourceLoader");
        properties.setProperty(PROPERTY_WEBAPP_LOADER_PATH, "/");

        /**layout配置*/
        properties.setProperty(PROPERTY_LAYOUT_DIR, DEFAULT_LAYOUT_DIR);
        properties.setProperty(PROPERTY_DEFAULT_LAYOUT, DEFAULT_DEFAULT_LAYOUT);
        properties.setProperty(PROPERTY_ERROR_TEMPLATE, DEFAULT_ERROR);

        Properties p = loadVelocityProperties();
        if (p.containsKey(PROPERTY_ERROR_TEMPLATE)) {
            properties.setProperty(PROPERTY_ERROR_TEMPLATE, p.getProperty(PROPERTY_ERROR_TEMPLATE));
        }
        if (p.containsKey(PROPERTY_LAYOUT_DIR)) {
            properties.setProperty(PROPERTY_LAYOUT_DIR, p.getProperty(PROPERTY_LAYOUT_DIR));
        }
        if (p.containsKey(PROPERTY_DEFAULT_LAYOUT)) {
            properties.setProperty(PROPERTY_DEFAULT_LAYOUT, p.getProperty(PROPERTY_DEFAULT_LAYOUT));
        }

        //        if (p.containsKey(PROPERTY_TOOLS_BOX)) {
        //            toolbox = p.getProperty(PROPERTY_TOOLS_BOX);
        //        }

        layout = properties.getProperty(PROPERTY_LAYOUT_DIR)
                 + properties.getProperty(PROPERTY_DEFAULT_LAYOUT);

        LOG.info("velocity layout path : " + layout);
    }

    private static VelocityEngine getVelocityEngine() {
        if (ve != null) {
            return ve;
        }

        initVelovityProperties();
        initToolbox();

        ve = new VelocityEngine();
        ve.setApplicationAttribute(SERVLET_CONTEXT_KEY, JFinal.me().getServletContext());
        ve.init(properties);
        return ve;
    }

    private static void initToolbox() {
        //            if (toolbox != null) {
        //                toolboxManager = ServletToolboxManager.getInstance(JFinal.me().getServletContext(),
        //                    toolbox);
        //            }
    }

    public void render() {
        Writer writer = null;
        try {
            Context context = createContext();

            initHttpAttributeContext(context, request);

            //view writer
            StringWriter viewWriter = new StringWriter();
            mergeTemplate(this.view, context, viewWriter);

            //layout writer
            StringWriter layoutWriter = new StringWriter();
            context.put(KEY_SCREEN_CONTENT, viewWriter);
            mergeTemplate(layout, context, layoutWriter);

            response.setContentType(contentType);
            writer = response.getWriter();
            writer.write(layoutWriter.toString());
            writer.flush();
        } catch (ResourceNotFoundException e) {
            throw new RenderException("template not exist:" + view, e);
        } catch (ParseErrorException e) {
            throw new RenderException("template parse fail:" + view + ":" + e, e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RenderException(e);
        } finally {
            if (writer == null) {
                return;
            }
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected Context createContext() {
        Context ctx = new VelocityContext();
        return ctx;
    }

    private static void mergeTemplate(String path, Context context, Writer writer) {
        VelocityEngine ve = getVelocityEngine();
        Template template = ve.getTemplate(path, encoding);
        template.merge(context, writer);
    }

    @SuppressWarnings("unchecked")
    private void initHttpAttributeContext(Context context, final HttpServletRequest request) {
        for (Enumeration<String> attrs = request.getAttributeNames(); attrs.hasMoreElements();) {
            String attrName = attrs.nextElement();
            context.put(attrName, request.getAttribute(attrName));
        }
    }

}