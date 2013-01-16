/**
 * 
 * Copyright (c) 2012 All Rights Reserved.
 */
package com.fredzhu.childredhome.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;

/**
 *                       
 * @Filename: BaseController.java
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
 *<li>Date: 2013-1-13</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public class BaseController extends Controller {

    protected static final Integer SIZE           = 20;

    protected static final String  MESSAGE        = "message";
    protected static final String  RESULT         = "result";
    protected static final String  RESULT_SUCCESS = "success";
    protected static final String  RESULT_FAIL    = "fail";

    protected static final String  MENU           = "menu";

    @SuppressWarnings("rawtypes")
    protected Map<String, Object> buildPagination(List list, Integer count) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", count);
        map.put("rows", list);
        return map;
    }
}
