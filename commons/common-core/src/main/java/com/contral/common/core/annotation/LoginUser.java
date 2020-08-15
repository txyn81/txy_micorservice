package com.contral.common.core.annotation;

import java.lang.annotation.*;

/**
 * @description: 请求的方法参数SysUser上添加该注解，这注入当前登陆人信息
 * @author: oren.tang
 * @date: 2020/8/12 9:22 下午
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginUser {

    boolean isFull() default false;
}
