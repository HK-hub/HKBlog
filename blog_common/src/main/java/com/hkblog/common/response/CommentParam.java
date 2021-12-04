package com.hkblog.common.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author : HK意境
 * @ClassName : CommentParam
 * @date : 2021/11/28 15:21
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@ToString
@NoArgsConstructor
public class CommentParam {

    @JsonProperty("")
    private String postId ;
    private String content ;
    private String parentId ;
    private String toUserId ;



}
