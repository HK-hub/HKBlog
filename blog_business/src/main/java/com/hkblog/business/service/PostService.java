package com.hkblog.business.service;

import com.hkblog.common.response.PageParam;
import com.hkblog.common.response.PostParam;
import com.hkblog.domain.entity.Post;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hkblog.domain.entity.User;
import com.hkblog.domain.vo.PostArchiveVo;
import com.hkblog.domain.vo.PostVo;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;

/**
 * @ClassName : PostService
 * @author : HK意境
 * @date : 2021/11/26
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public interface PostService extends IService<Post> {


    // 分页显示博客文章
    List<PostVo> listPost(PageParam pageParam);



    // 根据标签查询分页查询文章
    List<PostVo> findByTag(String tag, @Nullable PageParam pageParam);


    // 返回系统热门文章：自定义规则
    List<Post> getHotPosts(Integer num, Map<String, String> orderCondition);


    // 返回最新文章
    List<Post> getNewPosts(Integer num, Map orderCondition);


    // 返回文章按照年月份进行归档
    List<PostArchiveVo> listArchives();


    // 查询文章详情，标签，分类，作者，内容，标题等等
    PostVo getPostDetailVoById(String id);



    // 通过文章id 获取作者信息
    User getPostAuthorById(String postId);



    // 更新文章的评论数量
    Boolean updatePostCommentNum(String postId);

    // 发布新文章
    Post saveNewPost(PostParam postParam,String userId);



    // 根据分类id 查找全部文章
    List<PostVo> findAllPostByCategory(String categoryId);


    // 跟新文章点赞数量
    Boolean updatePostLikeNum(String id);
}
