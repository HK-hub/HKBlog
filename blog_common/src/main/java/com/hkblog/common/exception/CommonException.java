package com.hkblog.common.exception;


import com.hkblog.common.response.ResultCode;
import lombok.Data;

/**
 * 自定义异常
 */
@Data
public class CommonException extends Exception  {

    private ResultCode resultCode;

    public CommonException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
