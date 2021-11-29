package com.hkblog.business.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : HK意境
 * @ClassName : TestController
 * @date : 2021/11/27 19:25
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@CrossOrigin
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){

        return "测试接口哦";
    }


}
