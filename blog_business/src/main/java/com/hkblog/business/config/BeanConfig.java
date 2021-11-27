package com.hkblog.business.config;

import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
import com.hkblog.common.utils.IdWorker;
import nonapi.io.github.classgraph.json.Id;
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
    public IKeyGenerator keyGenerator() {
        return new H2KeyGenerator();
    }

}
