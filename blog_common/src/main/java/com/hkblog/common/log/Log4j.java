package com.hkblog.common.log;

/**
 * @author : HK意境
 * @ClassName : Log4j
 * @date : 2021/11/29 15:06
 * @description : 自定义注解，实现面向切面编程的日志记录
 * @Todo : 自定义注解，日志记录
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log4j {

    // 那个模块那个方法
    String module() default "";
    // 进行何种操作
    String operation() default "" ;

}
