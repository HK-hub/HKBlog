package com.hkblog.domain.vo;

import com.hkblog.domain.entity.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author : HK意境
 * @ClassName : CommentVo
 * @date : 2021/11/28 14:05
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@ToString
@NoArgsConstructor
public class CommentVo {

    // 评论id
    private String id ;

    // 文章id
    private String postId;

    // 作者信息
    private UserVo author ;

    // 评论内容
    private String content;

    // 子评论
    private List<CommentVo> children ;

    // 评论时间
    private Date createTime ;

    // 评论层级
    private Integer level;

    // 评论来源用户
    private UserVo fromUser ;

    // 对那个用户评论
    private UserVo toUser ;


    /**
     * @methodName : copy 函数
     * @author : HK意境
     * @date : 2021/11/28 14:26
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
    public void copy(Comment comment){

        this.id = comment.getCommentId() ;
        this.postId = comment.getPostId() ;
        this.content = comment.getContent() ;
        this.createTime = comment.getCreateTime() ;
        this.level = comment.getLevel() ;
    }




}
