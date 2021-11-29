package com.hkblog.business.service;

import com.hkblog.common.response.LoginParam;
import com.hkblog.common.response.RegisterParam;
import com.hkblog.common.response.ResponseResult;
import com.hkblog.domain.entity.User;

/**
 * @author : HK意境
 * @ClassName : LoginService
 * @date : 2021/11/27 10:26
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public interface LoginService {

    // 使用账号，密码登录
    String accountLogin(LoginParam loginParam);

    // 退出登录
    boolean logout(String token);


    // 使用账号密码，进行注册
    ResponseResult accountRegister(RegisterParam registerParam);
}
