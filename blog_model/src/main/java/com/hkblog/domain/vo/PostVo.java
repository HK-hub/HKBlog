package com.hkblog.domain.vo;

import com.hkblog.domain.entity.Post;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author : HK意境
 * @ClassName : PostVo
 * @date : 2021/11/26 9:44
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@Accessors(chain = true)
public class PostVo extends Post {

    private String author;
    private String avatarUrl ;
    private List<TagVo> tagList ;
    private List<CategoryVo> category ;

    // 去除 targlist 标签集合
    public void removeTag(){
        this.tagList.clear();
    }

    // 去除分类集合
    public void removeCategory(){
        this.category.clear();
    }

    // 去除作者信息


}


