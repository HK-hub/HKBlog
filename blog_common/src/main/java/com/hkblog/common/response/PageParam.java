package com.hkblog.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author : HK意境
 * @ClassName : PageParam
 * @date : 2021/11/26 9:42
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PageParam {

    // 当前页
    private Integer page = 1 ;

    //每页数量
    private Integer pageSize = 10;

    private String categoryId ;

    private String tagId ;

    private String year;

    private String month ;

    public PageParam(int page, int size){

        this.page = page;
        this.pageSize = size ;

    }

    // 处理月份
    public String getMonth() {
        /*if (this.month != null && this.month.length() == 1){
            return "0"+this.month ;
        }*/
        return this.month ;
    }

}
