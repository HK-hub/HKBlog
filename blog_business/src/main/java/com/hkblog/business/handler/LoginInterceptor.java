package com.hkblog.business.handler;

import com.alibaba.fastjson.JSON;
import com.hkblog.business.service.UserService;
import com.hkblog.business.service.impl.UserServiceImpl;
import com.hkblog.common.response.ResponseResult;
import com.hkblog.common.response.ResultCode;
import com.hkblog.domain.entity.User;
import com.hkblog.domain.thread.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : HK意境
 * @ClassName : LoginInterceptor
 * @date : 2021/11/27 16:18
 * @description : 登录拦截器
 * @Todo : 实现统一权限，认证鉴定
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserServiceImpl userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 再执行 Controller 方法之前进行执行
        /***
         *  需要判断请求的接口路径是否未 HandlerMethod （Controller 中的方法）
         *  判断 token 是否为空， 如果为空，未登录
         *      token 不为空，进行登录验证
         */
        if (!(handler instanceof HandlerMethod)){
            // 不是访问的 Controller 方法， 有可能为资源handler
            // 放行
            return true ;
        }
        // handler 是 HalderMethod
        String token = request.getHeader("Authorization");

        log.info("=========================== request start ==============================");
        StringBuffer requestURL = request.getRequestURL();
        log.info("request url:{}",requestURL);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}",token);
        log.info("=========================== request end ================================");

        if (StringUtils.isEmpty(token)){
            // 未登录
            ResponseResult<String> result = new ResponseResult<>(ResultCode.UNAUTHORIZED, "您还未登录哦!!");
            // 设置响应数据格式
            response.setContentType("application/json;charset=utf-8");
            // 响应数据
            response.getWriter().print(JSON.toJSONString(result));
            return false ;
        }

        User userByToken = userService.findUserByToken(token);
        if (userByToken == null) {
            // 未登录
            ResponseResult<String> result = new ResponseResult<>(ResultCode.UNAUTHORIZED, "未登录");
            // 设置响应数据格式
            response.setContentType("application/json;charset=utf-8");
            // 响应数据
            response.getWriter().print(JSON.toJSONString(result));
            return false ;
        }

        // 设置线程共享资源
        UserThreadLocal.put(userByToken);

        // 登录验证成功 放行
        return true ;
    }


    
    /**
     * @methodName : 删除 UserThreadLocal 中的数据，释放资源,如果不删除，会浪费系统资源
     * @author : HK意境
     * @date : 2021/11/27 19:46
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
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.remove();
    }
}
