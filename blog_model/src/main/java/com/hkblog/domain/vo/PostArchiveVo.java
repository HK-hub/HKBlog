package com.hkblog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author : HK意境
 * @ClassName : PostArchiveVo
 * @date : 2021/11/26 20:51
 * @description : 博客文章归档VO对象
 * @Todo : 按照年月份进行归档
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PostArchiveVo {

    private Integer year ;
    private Integer month ;
    private Integer count ;


}
