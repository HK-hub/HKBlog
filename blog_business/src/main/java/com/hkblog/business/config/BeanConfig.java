package com.hkblog.business.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
import com.hkblog.common.log.LogAspect;
import com.hkblog.common.utils.EncryptUtil;
import com.hkblog.common.utils.IdWorker;
import com.hkblog.common.utils.JwtUtils;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : HK意境
 * @ClassName : BeanConfig
 * @date : 2021/11/26 0:54
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Configuration
public class BeanConfig {

    @Bean
    public IdWorker getIdWorker(){
        return new IdWorker();
    }

    @Bean
    public JwtUtils jwtUtils(){

        return new JwtUtils();

    }

    @Bean
    public EncryptUtil encryptUtil(){
        return new EncryptUtil();
    }


    @Bean
    public ServletRegistrationBean<StatViewServlet> druidStatViewServlet() {
        ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<>(new StatViewServlet(),  "/druid/*");
        registrationBean.addInitParameter("allow", "127.0.0.1");// IP白名单 (没有配置或者为空，则允许所有访问)
        registrationBean.addInitParameter("deny", "");// IP黑名单 (存在共同时，deny优先于allow)
        registrationBean.addInitParameter("loginUsername", "root");
        registrationBean.addInitParameter("loginPassword", "root");
        registrationBean.addInitParameter("resetEnable", "false");
        return registrationBean;
    }


    // 日志
    @Bean
    public LogAspect logAspect(){

        return new LogAspect();

    }



}
