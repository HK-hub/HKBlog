package com.hkblog.common.exception;

import com.hkblog.common.response.ResponseResult;
import com.hkblog.common.response.ResultCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义的公共异常处理器 : 对加了 @Controller 的类进行拦截处理， AOP 实现
 *      1.声明异常处理器
 *      2.对异常统一处理
 * @author HK意境
 */
@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseResult error(HttpServletRequest request, HttpServletResponse response, Exception e) {
        if(e.getClass() == CommonException.class) {
            //类型转型
            CommonException ce = (CommonException) e;
            ResponseResult result = new ResponseResult(ce.getResultCode());
            return result;
        }else{
            ResponseResult result = new ResponseResult(ResultCode.SERVER_ERROR);
            return result;
        }
    }
}
