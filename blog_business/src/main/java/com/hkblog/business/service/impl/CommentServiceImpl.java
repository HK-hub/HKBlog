package com.hkblog.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkblog.business.mapper.PostMapper;
import com.hkblog.common.response.CommentParam;
import com.hkblog.common.response.ResponseResult;
import com.hkblog.common.utils.IdWorker;
import com.hkblog.domain.entity.Comment;
import com.hkblog.business.service.CommentService;
import com.hkblog.business.mapper.CommentMapper;
import com.hkblog.domain.entity.User;
import com.hkblog.domain.thread.UserThreadLocal;
import com.hkblog.domain.vo.CommentVo;
import com.hkblog.domain.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

    @Autowired
    private CommentMapper commentMapper ;
    @Autowired
    private UserServiceImpl userService ;
    @Autowired
    private IdWorker idWorker ;
    @Autowired
    private ThreadService threadService;
    @Autowired
    private PostMapper postMapper ;

    /**
     * @methodName :
     * @author : HK意境
     * @date : 2021/11/28 13:52
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
    @Override
    public ResponseResult commentPost(Comment comment, String postId) {
        return null;
    }


    /**
     * @methodName : 查找文章的全部评论
     * @author : HK意境
     * @date : 2021/11/28 13:54
     * @description :
     * @Todo : 评论列表，作者信息，评论者信息，子评论
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @Override
    public List<CommentVo> findPostComments(String postId) {

        /**
         * 1. 根据文章id 查询评论列表
         * 2. 根据作者id 查询作者信息
         * 3. 判断 level = 1 ，去查询子评论
         * 3. 如果有子评论，在根据评论parent_id 去查询
         */
        // 查询文章直接评论，一级评论
        List<Comment> oneLevelComments  = commentMapper.selectList(new LambdaQueryWrapper<Comment>()
                .eq(Comment::getPostId, postId).eq(Comment::getLevel, "1"));

        // 获取 展示comment CommentVo
        List<CommentVo> commentVoList = this.commentListToVoList(oneLevelComments);


        return commentVoList;
    }


    /**
     * @methodName : 保存用户评论
     * @author : HK意境
     * @date : 2021/11/28 15:26
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
    @Override
    @Transactional
    public Comment saveComment(CommentParam commentParam) {

        // 获取当前线程池中的用户
        User currentUser = UserThreadLocal.get();
        Comment comment = new Comment();

        // 设置属性
        comment.setCommentId(idWorker.nextId()+"");
        comment.setContent(commentParam.getContent());
        comment.setPostId(commentParam.getPostId());
        // 设置评论来源
        comment.setFromUserId(currentUser.getUserId());
        // 设置作者Id
        User author = userService.findUserByPostId(commentParam.getPostId());
        comment.setAuthorId(author.getUserId());

        //设置评论去向
        comment.setToUserId(commentParam.getToUserId());

        // 设置评论层级
        String parentId = commentParam.getParentId();
        if (StringUtils.isEmpty(parentId) || "000".equals(parentId)){
            // 没有父评论
            comment.setLevel(1);
        }else{
            comment.setLevel(2);
        }
        // 设置父评论
        comment.setParentId(parentId == null || "".equals(parentId) ? "000" : parentId);
        comment.setCreateTime(new Date());
        comment.setUpdateTime(comment.getCreateTime());
        commentMapper.insert(comment);

        return comment;
    }


    /**
     * @methodName : 将 commentList 对象转换为 commentVo 对象
     * @author : HK意境
     * @date : 2021/11/28 14:19
     * @description :
     * @Todo : 封装user, 子评论，用户信息，时间等
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    private List<CommentVo> commentListToVoList(List<Comment> oneLevelComments) {

        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : oneLevelComments) {
            commentVoList.add(this.commentToVo(comment));
        }
        return commentVoList ;
    }


    /**
     * @methodName : 将 comment 对象转换为 commentVo 对象
     * @author : HK意境
     * @date : 2021/11/28 14:21
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
    private CommentVo commentToVo(Comment comment) {

        CommentVo commentVo = new CommentVo();
        commentVo.copy(comment);

        // 作者信息
        String authorId = comment.getAuthorId();
        UserVo author = userService.getUserVo(authorId);
        commentVo.setAuthor(author);

        // 评论来源用户
        System.out.println();
        UserVo fromUserVo = userService.getUserVo(comment.getFromUserId());
        commentVo.setFromUser(fromUserVo);

        // 子评论
        Integer level = comment.getLevel();
        if (1 == level){
            // 具有子评论
            String parentId = comment.getCommentId();
            List<CommentVo> childComments = findCommentsByParentId(parentId);
            commentVo.setChildren(childComments);
        }

        // 被评论者信息 toUser ,给谁评论
        if (level > 1){

            String toUserId = comment.getToUserId();

            UserVo toUser = userService.getUserVo(toUserId);
            commentVo.setToUser(toUser);
        }

        return commentVo ;

    }


    /**
     * @methodName : 获取文章评论的所有子评论
     * @author : HK意境
     * @date : 2021/11/28 14:43
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
    private List<CommentVo> findCommentsByParentId(String parentId) {

        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId, parentId);
        queryWrapper.eq(Comment::getLevel , 2);
        List<Comment> commentList = commentMapper.selectList(queryWrapper);
        return commentListToVoList(commentList) ;
    }
}




