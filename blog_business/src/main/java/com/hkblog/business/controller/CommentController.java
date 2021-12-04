package com.hkblog.business.controller;

import com.hkblog.business.service.PostService;
import com.hkblog.business.service.impl.CommentServiceImpl;
import com.hkblog.business.service.impl.PostServiceImpl;
import com.hkblog.business.service.impl.ThreadService;
import com.hkblog.common.response.CommentParam;
import com.hkblog.common.response.ResponseResult;
import com.hkblog.common.response.ResultCode;
import com.hkblog.domain.entity.Comment;
import com.hkblog.domain.entity.User;
import com.hkblog.domain.thread.UserThreadLocal;
import com.hkblog.domain.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

/**
 * @author : HK意境
 * @ClassName : CommentController
 * @date : 2021/11/26 0:05
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@CrossOrigin
@RefreshScope
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentServiceImpl commentService ;
    @Autowired
    private PostServiceImpl postService ;

    /**
     * @methodName : 对文章进行评论
     * @author : HK意境
     * @date : 2021/11/28 13:36
     * @description : 评论文章
     * @Todo : 需要文章ID参数，评论实体
     * @params :
         * @param : postId
         * @param : comment
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @PostMapping("/create")
    @Transactional
    public ResponseResult commentPost(@RequestBody CommentParam commentParam,
                                        @RequestHeader("Authorization") String token){
        try{
            System.out.println("commentParam: " + commentParam.toString());

            Comment comment = commentService.saveComment(commentParam, token);

            // 使用多线程，线程池技术去更新文章评论数量
            postService.updatePostCommentNum(commentParam.getPostId());

            return new ResponseResult(ResultCode.SUCCESS,comment);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null ;
    }


    /**
     * @methodName : 通过文章id 查询全部的文章评论
     * @author : HK意境
     * @date : 2021/11/28 13:42
     * @description :
     * @Todo : 查询文章的全部评论，封装为 commentVo 对象
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @GetMapping("/{id}")
    public ResponseResult findCommentByPostId(@PathVariable String id){

        List<CommentVo> commentVoList = commentService.findPostComments(id);
        return new ResponseResult(ResultCode.SUCCESS, commentVoList);
    }


}
