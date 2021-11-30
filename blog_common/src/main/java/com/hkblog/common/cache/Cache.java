package com.hkblog.common.cache;


import java.lang.annotation.*;

/**
 * @author : HK意境
 * @ClassName : Cache
 * @date : 2021/11/30 16:27
 * @description : 自定义注解，实现数据的加载进入内存进行高速缓存
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

    // 缓存过期时间
    long expire() default 1 * 60 * 1000 ;

    //缓存标识 key
    String name() default "";

}
