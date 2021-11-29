package com.hkblog.common.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author : HK意境
 * @ClassName : PostBodyParam
 * @date : 2021/11/28 21:26
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@NoArgsConstructor
@ToString
public class PostBodyParam {

    private String content;
    private String contentHtml ;


}
