package com.hkblog.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hkblog.business.mapper.PostMapper;
import com.hkblog.domain.entity.Post;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author : HK意境
 * @ClassName : ThreadService
 * @date : 2021/11/28 10:48
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Component
public class ThreadService {




    /**
     * @methodName :
     * @author : HK意境
     * @date : 2021/11/28 10:51
     * @description : 使用多线程进行文章阅读次数的增加
     * @Todo : 期望此操作在线程池执行，不会影响原有的主线程
     * @params : 
         * @param : null 
     * @return : null
     * @throws: 
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @Trace
    @Async("taskExecutor")
    public Boolean updatePostViewNum(PostMapper postMapper, Post post) {

        // 获取跟新对象
        Integer viewNum = post.getViewNum();
        Post updatePost = new Post();
        // 设置浏览量加一
        updatePost.setViewNum(viewNum + 1);
        LambdaUpdateWrapper<Post> wrapper = new LambdaUpdateWrapper<Post>().eq(Post::getPostId, post.getPostId());

        // 设置多线程下的一个线程安全
        wrapper.eq(Post::getViewNum , viewNum);

        // 更新数据库
        int update = postMapper.update(updatePost, wrapper);

        return update != 0;
    }



    /**
     * @methodName : 跟新文章评论数量
     * @author : HK意境
     * @date : 2021/11/28 16:01
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
    @Trace
    @Async("taskExecutor")
    public Boolean updatePostCommentNum(PostMapper postMapper, Post post) {

        // 获取跟新对象
        Integer commentNum = post.getCommentNum();
        Post updatePost = new Post();
        // 设置浏览量加一
        updatePost.setCommentNum(commentNum + 1);
        LambdaUpdateWrapper<Post> wrapper = new LambdaUpdateWrapper<Post>().eq(Post::getPostId, post.getPostId());

        // 设置多线程下的一个线程安全
        wrapper.eq(Post::getCommentNum , commentNum);

        // 更新数据库
        int update = postMapper.update(updatePost, wrapper);

        return update != 0;
    }

    @Trace
    @Async("taskExecutor")
    public Boolean updatePostLikeNum(PostMapper postMapper ,Post post) {

        Integer likeNum = post.getLikeNum();
        Post updatePost = new Post();
        // 设置点赞量加一
        updatePost.setLikeNum(likeNum + 1);
        LambdaUpdateWrapper<Post> wrapper = new LambdaUpdateWrapper<Post>().eq(Post::getPostId, post.getPostId());

        // 设置多线程下的一个线程安全
        wrapper.eq(Post::getLikeNum , likeNum);

        // 更新数据库
        int update = postMapper.update(updatePost, wrapper);

        return update != 0;

    }

    @Trace
    @Async
    public Boolean updatePostCollectNum(PostMapper postMapper, Post post, Integer ops) {

        Integer collectionNum = post.getCollectionNum();
        Post updatePost = new Post();

        // 设置收藏数量加一
        updatePost.setCollectionNum(collectionNum + ops);
        LambdaUpdateWrapper<Post> wrapper = new LambdaUpdateWrapper<Post>().eq(Post::getPostId, post.getPostId());

        // 设置多线程下的一个线程安全
        wrapper.eq(Post::getCollectionNum , collectionNum);
        // 更新数据库
        int update = postMapper.update(updatePost, wrapper);

        return update != 0;

    }
}
