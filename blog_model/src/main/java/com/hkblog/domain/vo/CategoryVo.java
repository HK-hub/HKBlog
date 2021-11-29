package com.hkblog.domain.vo;

import com.hkblog.domain.entity.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : HK意境
 * @ClassName : CategoryVo
 * @date : 2021/11/26 10:42
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@NoArgsConstructor
public class CategoryVo{

    private String id;
    private String avatar ;
    private String categoryName ;
    private String description ;

    public CategoryVo(Category category){
        this.id = category.getId() ;
        this.categoryName = category.getName();
        this.avatar = category.getAvatar();
        this.description = category.getDescription() ;
    }

}
