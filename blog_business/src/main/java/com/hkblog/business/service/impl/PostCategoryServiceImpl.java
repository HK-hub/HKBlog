package com.hkblog.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkblog.domain.entity.PostCategory;
import com.hkblog.business.service.PostCategoryService;
import com.hkblog.business.mapper.PostCategoryMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class PostCategoryServiceImpl extends ServiceImpl<PostCategoryMapper, PostCategory>
    implements PostCategoryService{

}




