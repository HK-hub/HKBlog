package com.hkblog.business.service;

import com.hkblog.common.response.PostParam;
import com.hkblog.domain.entity.PostCategory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface PostCategoryService extends IService<PostCategory> {


    // 保存文章分类信息关系
    Boolean savePostCategory(PostParam postParam);

}
