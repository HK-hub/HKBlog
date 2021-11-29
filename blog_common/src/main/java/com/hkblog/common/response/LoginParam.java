package com.hkblog.common.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : HK意境
 * @ClassName : LoginParam
 * @date : 2021/11/27 10:30
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@NoArgsConstructor
public class LoginParam {

    private String account ;
    private String password ;

}
