package com.hkblog.auth.controller;

import com.hkblog.auth.service.impl.LoginServiceImpl;
import com.hkblog.common.constant.CodeTypeConstant;
import com.hkblog.common.response.LoginParam;
import com.hkblog.common.response.RegisterParam;
import com.hkblog.common.response.ResponseResult;
import com.hkblog.common.response.ResultCode;
import com.hkblog.common.utils.AuthRequestUtils;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private LoginServiceImpl loginService ;
    @Autowired
    private AuthRequestUtils authRequestUtils ;




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

    // 发送邮箱登录验证码
    @GetMapping("/emailCode")
    public ResponseResult sendCode(@RequestParam(name = "email")String email){

        boolean res = loginService.sendCode(email, CodeTypeConstant.EMAIL);
        if (res){
            return ResponseResult.SUCCESS().setData("邮箱验证码已发送!") ;
        }else{
            return ResponseResult.FAIL().setData("验证码发送失败!");
        }
    }



    /**
     * @methodName : 邮箱登录，
     * @author : HK意境
     * @date : 2021/11/30 21:58
     * @description :
     * @Todo : 使用邮箱登录
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @PostMapping("/emailLogin")
    public ResponseResult emailLogin(@RequestParam(name = "email")String email,
                                     @RequestParam(name ="code")String code){

        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(code)){
            // 邮箱或验证码为空
            return new ResponseResult(ResultCode.BAD_REQUEST);
        }else{
            // 进行校验 redis 缓存的验证码
            System.out.println("进入redis 验证校验: ");
            String token = loginService.emailLogin(email);
            if (!StringUtils.isEmpty(token)){
                return new ResponseResult(ResultCode.SUCCESS,token);
            }
        }

        return new ResponseResult(ResultCode.FAIL,"验证码过期请重新获取!") ;
    }



    /**
     * @methodName : Gitee 登录, 获取授权页面
     * @author : HK意境
     * @date : 2021/12/1 14:31
     * @description :
     * @Todo : 发起登录，获取授权页面
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @RequestMapping(value = "/render/{source}",method = RequestMethod.GET)
    public void renderAuth(HttpServletResponse response,
                           @PathVariable(required = false) String source) throws IOException {

        if (!StringUtils.isEmpty(source)){
            // source 源验证合法

            if ("github".equals(source)){
                // Github 登录
                AuthRequest authRequest = authRequestUtils.getGithubAuthRequest();
                response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
            }else if ("gitee".equals(source)){
                // Gitee 登录
                AuthRequest authRequest = authRequestUtils.getGiteeAuthRequest();
                response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
            }else{
                // 默认使用gitee 登录
                AuthRequest authRequest = authRequestUtils.getGiteeAuthRequest();
                response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
            }
        }

    }


    /**
     * @methodName : Gitee ， Github 登录，授权页面登录回调地址
     * @author : HK意境
     * @date : 2021/12/1 14:42
     * @description :
     * @Todo :
     * @params :
         * @param : source
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @RequestMapping("/callback/{source}")
    public ResponseResult login(AuthCallback callback, @PathVariable String source) {

        AuthResponse authResponse = null;

        switch (source){
            case "gitee":
                AuthRequest authRequest = authRequestUtils.getGiteeAuthRequest();
                authResponse = authRequest.login(callback);
                break;
            case "github":
                AuthRequest githubAuthRequest = authRequestUtils.getGithubAuthRequest();
                authResponse = githubAuthRequest.login(callback);
                break;
            default:
                break;
        }

        if (authResponse != null){
            // 具有登录回调，返回信息了
            String token = loginService.oAuthLogin(authResponse);
            if (StringUtils.isEmpty(token)){
                // token 为空
                return new ResponseResult(ResultCode.USER_HAS_EXITS);
            }else {
                return new ResponseResult(ResultCode.SUCCESS, token) ;
            }
        }

        return ResponseResult.FAIL().setData("第三方登录回调失败!");
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
