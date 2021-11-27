package com.hkblog.common.controller;

import com.hkblog.common.response.ResponseResult;
import com.hkblog.common.response.ResultCode;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

/**
 * @author : HK意境
 * @ClassName : ErrorController
 * @date : 2021/11/17 14:54
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@RefreshScope
@CrossOrigin
@RestController
@RequestMapping("/error")
public class ErrorController {


    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public ResponseResult errors(@RequestParam(name = "code",required = false)int code){
       if (code == 1){
           return new ResponseResult(ResultCode.UNAUTHENTICATED) ;
       }else if (code == 2){
           return new ResponseResult(ResultCode.UNAUTHORIZED) ;
       }else {
           return new ResponseResult(ResultCode.UNAUTHENTICATED) ;
       }

    }


    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseResult error(@RequestParam(name = "errorCode")Integer errorCode){

        if (errorCode == 0){

            return new ResponseResult(ResultCode.SERVER_BUSY);
        }else{
            return new ResponseResult(ResultCode.SERVER_ERROR);
        }
    }


}
