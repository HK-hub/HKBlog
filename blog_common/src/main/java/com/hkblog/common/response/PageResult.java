package com.hkblog.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;

import java.util.List;

/**
 * @ClassName : PageResult
 * @author : HK意境
 * @date : 2021/11/26
 * @description : 公共分页
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    private Long totalNumbers;
    private Long currentPage = 1L;
    private Long totalPages ;
    private Long pageSize = 10L ;
    private Long numOfElements = 10L;
    private List<T> rows;
}
