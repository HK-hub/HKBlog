package com.hkblog.business.mapper;

import com.hkblog.domain.entity.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hkblog.domain.vo.PostArchiveVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Entity com.hkblog.domain.entity.Post
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {


    // 通过标签分页查询
    List<Post> selectByTagId(String tagId, Integer page, Integer size);

    // 查询文章归档
    List<PostArchiveVo> selectArchiveList();



    // 通过文章分类信息查询全部博客文章
    List<Post> selectListByCategory(String categoryId);

    // 通过标签查询全部文章



}




