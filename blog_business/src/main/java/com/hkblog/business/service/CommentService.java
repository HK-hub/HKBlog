package com.hkblog.business.service;

import com.hkblog.common.response.CommentParam;
import com.hkblog.common.response.ResponseResult;
import com.hkblog.domain.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hkblog.domain.vo.CommentVo;

import java.util.List;

/**
 *
 */
public interface CommentService extends IService<Comment> {
    
    /**
     * @methodName : 通过文章id 查询全部文章评论
     * @author : HK意境
     * @date : 2021/11/28 13:42
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
    ResponseResult commentPost(Comment comment, String postId);
    
    
    /**
     * @methodName : 查找文章的全部评论
     * @author : HK意境
     * @date : 2021/11/28 13:55
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
    List<CommentVo> findPostComments(String postId);

    /**
     * @methodName : 保存用户对于文章的评论
     * @author : HK意境
     * @date : 2021/11/28 15:27
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
    Comment saveComment(CommentParam commentParam, String token);
}
