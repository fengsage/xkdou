/**
 * Copyright (c) 2012 All Rights Reserved.
 */
package com.fredzhu.childredhome.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *                       
 * @Filename PermissionOwn.java
 *
 * @Description 权限辅助注解,用于简化权限的配置,如果木有,亲需要手动在数据库里面添加
 *
 * @Version 1.0
 *
 * @Author fred
 *
 * @Email me@fengsage.com
 *       
 * @History
 *<li>Author: fred</li>
 *<li>Date: 2012-11-12</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionOwn {

    String alias() default "";

    String parent() default "";

    boolean isMenu() default false;

    String name();//权限名称

}
