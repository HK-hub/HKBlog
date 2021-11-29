package com.hkblog.common.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : HK意境
 * @ClassName : RegisterParam
 * @date : 2021/11/27 15:34
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@NoArgsConstructor
public class RegisterParam extends LoginParam {

    private String nickname;
}
