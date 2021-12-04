package com.hkblog.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 博客文章
 * @TableName tb_post
 */
// 设置elastic search 文档
/*
 * type : 字段数据类型
 * analyzer : 分词器类型
 * index : 是否索引 默认 :
 * Keyword : 短语 不进行分词
 */
@Document(indexName = "post", type = "post",shards = 3, replicas = 1)
@TableName(value ="tb_post")
@Data
public class Post implements Serializable {
    /**
     * 博客文章id 编号
     */
    @Id
    // 文章ID不能作为索引
    @Field(type = FieldType.Text , index = false)
    @TableId(value = "post_id", type = IdType.ASSIGN_ID)
    private String postId;

    /**
     * 文章作者id
     */
    @Field(type = FieldType.Text,index = false)
    @TableField
    private String userId ;

    /**
     * 文章封面
     */
    @Field(type = FieldType.Text ,index = false)
    @TableField(value = "cover_url")
    private String coverUrl;

    /**
     * 文章标题
     */
    @Field(type = FieldType.Text ,analyzer = "ik_max_word")
    @TableField(value = "title")
    private String title;

    /**
     * 文章描述
     */
    @Field(type = FieldType.Text ,analyzer = "ik_max_word")
    @TableField(value = "description")
    private String description;

    /**
     * 文章内容
     */
    @Field(type = FieldType.Text ,analyzer = "ik_max_word")
    @TableField(value = "content")
    private String content;

    /**
     * 文章浏览数量
     */
    @Field(type = FieldType.Integer ,index = false)
    @TableField(value = "view_num")
    private Integer viewNum;

    /**
     * 文章点赞数量
     */
    @Field(type = FieldType.Integer ,index = false)
    @TableField(value = "like_num")
    private Integer likeNum;

    /**
     * 文章收藏数量
     */
    @Field(type = FieldType.Integer ,index = false)
    @TableField(value = "collection_num")
    private Integer collectionNum;

    /**
     * 文章评论数量
     */
    @Field(type = FieldType.Integer ,index = false)
    @TableField(value = "comment_num")
    private Integer commentNum;

    /**
     * 文章状态：1上架,0下架
     */
    @Field(type = FieldType.Integer ,index = false)
    @TableField(value = "status")
    private Integer status;


    /***
     * 文章是否置顶
     */
    @Field(type = FieldType.Integer ,index = false)
    private Integer weight ;

    /**
     * 文章创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Field(type = FieldType.Date ,index = true)
    private Date createTime;

    /**
     * 文章更新时间
     */
    @Field(type = FieldType.Date ,index = true)
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 
     */
    @TableLogic
    @TableField(value = "deleted")
    @Field(type = FieldType.Boolean ,index = false)
    private Boolean deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Post other = (Post) that;
        return (this.getPostId() == null ? other.getPostId() == null : this.getPostId().equals(other.getPostId()))
            && (this.getCoverUrl() == null ? other.getCoverUrl() == null : this.getCoverUrl().equals(other.getCoverUrl()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getViewNum() == null ? other.getViewNum() == null : this.getViewNum().equals(other.getViewNum()))
            && (this.getLikeNum() == null ? other.getLikeNum() == null : this.getLikeNum().equals(other.getLikeNum()))
            && (this.getCollectionNum() == null ? other.getCollectionNum() == null : this.getCollectionNum().equals(other.getCollectionNum()))
            && (this.getCommentNum() == null ? other.getCommentNum() == null : this.getCommentNum().equals(other.getCommentNum()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPostId() == null) ? 0 : getPostId().hashCode());
        result = prime * result + ((getCoverUrl() == null) ? 0 : getCoverUrl().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getViewNum() == null) ? 0 : getViewNum().hashCode());
        result = prime * result + ((getLikeNum() == null) ? 0 : getLikeNum().hashCode());
        result = prime * result + ((getCollectionNum() == null) ? 0 : getCollectionNum().hashCode());
        result = prime * result + ((getCommentNum() == null) ? 0 : getCommentNum().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", postId=").append(postId);
        sb.append(", coverUrl=").append(coverUrl);
        sb.append(", title=").append(title);
        sb.append(", description=").append(description);
        sb.append(", content=").append(content);
        sb.append(", viewNum=").append(viewNum);
        sb.append(", likeNum=").append(likeNum);
        sb.append(", collectionNum=").append(collectionNum);
        sb.append(", commentNum=").append(commentNum);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deleted=").append(deleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}