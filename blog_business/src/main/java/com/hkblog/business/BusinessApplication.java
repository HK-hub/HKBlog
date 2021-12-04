package com.hkblog.business;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author : HK意境
 * @ClassName : BusinessApplication
 * @date : 2021/11/25 22:09
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@MapperScan("com.hkblog.business.mapper")
// 开启服务调用功能
@EnableFeignClients
@SpringBootApplication(scanBasePackages = "com.hkblog")
// 开启服务发现功能
@EnableDiscoveryClient
public class BusinessApplication {

    public static void main(String[] args) {

        SpringApplication.run(BusinessApplication.class , args);

    }

}
