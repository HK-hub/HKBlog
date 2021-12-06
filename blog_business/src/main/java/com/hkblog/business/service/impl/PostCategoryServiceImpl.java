package com.hkblog.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkblog.common.response.PostParam;
import com.hkblog.domain.entity.PostCategory;
import com.hkblog.business.service.PostCategoryService;
import com.hkblog.business.mapper.PostCategoryMapper;
import com.hkblog.domain.vo.CategoryVo;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 *
 */
@Service
public class PostCategoryServiceImpl extends ServiceImpl<PostCategoryMapper, PostCategory>
    implements PostCategoryService{

    @Autowired
    private PostCategoryMapper postCategoryMapper ;
    @Autowired
    private CategoryServiceImpl categoryService;

    @Override
    @Transactional
    @Trace
    public Boolean savePostCategory(PostParam postParam) {

        // 获取文章id
        String postId = postParam.getId();
        // 获取分类信息
        CategoryVo category = postParam.getCategory();
        String categoryId = category.getId();


        // 构造保存对象
        PostCategory postCategory = new PostCategory();
        postCategory.setPostId(postId);
        postCategory.setCategoryId(categoryId);
        postCategory.setCreateTime(new Date());
        postCategory.setUpdateTime(postCategory.getCreateTime());

        // 保存
        int insert = postCategoryMapper.insert(postCategory);

        return insert != 0 ;
    }
}




