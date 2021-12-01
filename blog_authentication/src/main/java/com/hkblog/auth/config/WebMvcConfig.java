package com.hkblog.auth.config;

import com.hkblog.auth.handler.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author : HK意境
 * @ClassName : WebMvcConfig
 * @date : 2021/11/28 21:49
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

    @Resource
    private LoginInterceptor loginInterceptor ;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截test接口，后续实际遇到需要拦截的接口时，在配置为真正的拦截接口
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/test")
                // 发表评论
                .addPathPatterns("/comment/create")
                // 分布文章
                .addPathPatterns("/post/publish")
                .addPathPatterns("post/new");
    }


}
