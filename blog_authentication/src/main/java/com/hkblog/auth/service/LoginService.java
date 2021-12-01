package com.hkblog.auth.service;

import com.hkblog.common.response.LoginParam;
import com.hkblog.common.response.RegisterParam;
import com.hkblog.common.response.ResponseResult;
import me.zhyd.oauth.model.AuthResponse;

/**
 * @author : HK意境
 * @ClassName : LoginService
 * @date : 2021/12/1 12:33
 * @description :
 * @Todo : 登录service
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public interface LoginService {

    // 邮箱登录
    public String emailLogin(String email);


    // 发送验证码: 邮箱验证码，短信验证码等等
    public boolean sendCode(String email, String type) ;


    // 使用账号，密码登录
    String accountLogin(LoginParam loginParam);

    // 退出登录
    boolean logout(String token);


    // 使用账号密码，进行注册
    ResponseResult accountRegister(RegisterParam registerParam);


    // 使用第三方账号登录，
    String oAuthLogin(AuthResponse authResponse);
}
