package com.hkblog.common.response;

import com.hkblog.domain.vo.CategoryVo;
import com.hkblog.domain.vo.TagVo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.log4j.Category;

import java.util.List;

/**
 * @author : HK意境
 * @ClassName : PostParam
 * @date : 2021/11/28 21:25
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@NoArgsConstructor
@ToString
public class PostParam {

    private String id;
    private String title ;
    private PostBodyParam body ;
    private String description ;
    // 文章封面: 默认
    private String coverUrl = "http://img.netbian.com/file/2021/0810/d33c96a71890de84c2bdc4847a1aeaf2.jpg";
    private CategoryVo category;
    private List<TagVo> tags ;

}
