package com.hkblog.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author : HK意境
 * @ClassName : AuthenticApplication
 * @date : 2021/11/27 10:17
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@SpringBootApplication(scanBasePackages = "com.hkblog")
@EnableDiscoveryClient
public class AuthenticApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticApplication.class, args);

    }


}
