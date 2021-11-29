package com.hkblog.business.mapper;

import com.hkblog.domain.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.hkblog.domain.entity.Category
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    // 根据文章Id查询文章所有分类信息
    List<Category> selectListByPostId(@Param("postId") String postId);
}




