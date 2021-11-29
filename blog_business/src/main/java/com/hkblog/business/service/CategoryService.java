package com.hkblog.business.service;

import com.hkblog.domain.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hkblog.domain.vo.CategoryVo;

import java.util.List;

/**
 *
 */
public interface CategoryService extends IService<Category> {


    // 根据文章ID 查询全部的分类信息
    List<Category> findCategoryListByPostId(String postId);


    // 获取全部分类信息 VO 对象
    List<CategoryVo> findAllCategoryVo();


    // 通过 vo 对象保存 category
    Boolean saveCategoryVo(CategoryVo category);
}
