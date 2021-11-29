package com.hkblog.business.controller;

import com.hkblog.business.service.impl.LoginServiceImpl;
import com.hkblog.business.service.impl.UserServiceImpl;
import com.hkblog.common.response.LoginParam;
import com.hkblog.common.response.RegisterParam;
import com.hkblog.common.response.ResponseResult;
import com.hkblog.common.response.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author : HK意境
 * @ClassName : LoginController
 * @date : 2021/11/26 0:05
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@CrossOrigin
@RestController
@RefreshScope
@RequestMapping()
public class LoginController {

    @Autowired
    private LoginServiceImpl loginService ;
    @Autowired
    private UserServiceImpl userService ;

    /**
     * @methodName : 用户登录：账号密码
     * @author : HK意境
     * @date : 2021/11/27 10:25
     * @description :
     * @Todo : 加密密码进行比对
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @PostMapping("/login")
    public ResponseResult accountLogin(@RequestBody LoginParam loginParam){

        String token = loginService.accountLogin(loginParam);

        if (StringUtils.isEmpty(token)){
            // 没有用户，为生产token
            return new ResponseResult(ResultCode.UNAUTHENTICATED,"用户名或密码错误!!!");
        }
        return new ResponseResult<String>(ResultCode.SUCCESS, token);
    }



    /**
     * @methodName : 用户退出登录，清除 redis 的信息缓存
     * @author : HK意境
     * @date : 2021/11/27 15:10
     * @description :
     * @Todo :
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public ResponseResult<Boolean> logout(@RequestHeader("Authorization") String token){

        // 获取 token
        // redis 去除 token 缓存
        boolean res = loginService.logout(token);

        return new ResponseResult<>(ResultCode.SUCCESS,res);
    }


    /**
     * @methodName : 注册用户：
     * @author : HK意境
     * @date : 2021/11/27 15:31
     * @description : 需要开启事务
     * @Todo : 通过账号，昵称，密码注册账号
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @Transactional
    @PostMapping("/register")
    public ResponseResult accountRegister(@RequestBody RegisterParam registerParam){

        ResponseResult responseResult = loginService.accountRegister(registerParam);
        return responseResult ;
    }


}
