package com.hkblog.extend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author : HK意境
 * @ClassName : ExtendApplication
 * @date : 2021/12/4 18:22
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
// 开启服务调用功能
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.hkblog")
public class ExtendApplication {

    public static void main(String[] args) {

        SpringApplication.run(ExtendApplication.class ,args) ;

    }

}
