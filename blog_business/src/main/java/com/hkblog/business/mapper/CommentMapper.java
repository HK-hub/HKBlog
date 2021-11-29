package com.hkblog.business.mapper;

import com.hkblog.domain.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.hkblog.domain.entity.Comment
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {


    /**
     * @methodName : 查询文章的全部评论
     * @author : HK意境
     * @date : 2021/11/28 13:56
     * @description :
     * @Todo :
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    List<Comment> selectCommentsByPostId(@Param("postId") String postId);
}




