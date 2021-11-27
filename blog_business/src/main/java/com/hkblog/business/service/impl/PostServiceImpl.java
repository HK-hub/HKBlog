package com.hkblog.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkblog.business.mapper.TagMapper;
import com.hkblog.business.mapper.UserMapper;

import com.hkblog.common.response.PageParam;
import com.hkblog.domain.entity.Post;
import com.hkblog.business.service.PostService;
import com.hkblog.business.mapper.PostMapper;
import com.hkblog.domain.entity.Tag;
import com.hkblog.domain.entity.User;
import com.hkblog.domain.vo.PostArchiveVo;
import com.hkblog.domain.vo.PostVo;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 *
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post>
    implements PostService{

    @Autowired
    private PostMapper postMapper ;
    @Autowired
    private TagMapper tagMapper ;
    @Autowired
    private UserMapper userMapper ;
    @Autowired
    private TagServiceImpl tagService;
    
    /**
     * @methodName : 分页查询post 文章
     * @author : HK意境
     * @date : 2021/11/26 10:07
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
    public List<PostVo> listPost(PageParam pageParam) {

        Page<Post> postPage = postMapper.selectPage(Page.of(pageParam.getPage(), pageParam.getPageSize()),
                new LambdaQueryWrapper<Post>()
                        .eq(Post::getStatus, 1)
                        .eq(Post::getDeleted, false)
                        // 置顶排序
                        .orderByDesc(Post::getWeight)
                        // 创建时间排序
                        .orderByDesc(Post::getCreateTime)
                        // 浏览量排序
                        .orderByDesc(Post::getViewNum));
        //System.out.println("总页数：" + postPage.getPages() + " 总条数：" + postPage.getTotal());
        List<Post> records = postPage.getRecords();

        List<PostVo> voList = new ArrayList<>();

        // 去为每一个record 查询标签
        for (Post record : records) {
            // 查询文章标签
            List<Tag> tags = tagMapper.selectPostTags(record.getPostId());
            User postAuthor = this.getPostAuthor(record);
            // 封装属性
            PostVo postVo = new PostVo();
            BeanUtils.copyProperties(record, postVo);
            // 设置标签
            postVo.setTagList(tagService.selectByTags(tags));
            // 设置作者信息
            postVo.setAuthor(postAuthor.getUsername()).setAvatarUrl(postAuthor.getAvatar());

            voList.add(postVo);
        }

        return voList;
    }


    /**
     * @methodName : 根据标签 tag 查询博客文章
     * @author : HK意境
     * @date : 2021/11/26 15:03
     * @description :
     * @Todo : 分页查询，
     * @params :
         * @param : pageParm 不是必须
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @Override
    public List<PostVo> findByTag(String tagName, @Nullable PageParam pageParam) {

        Integer page = 1 ;
        Integer size = 10 ;
        if (pageParam != null) {
            page = pageParam.getPage() ;
            size = pageParam.getPageSize() ;
        }

        // 通过 tagName 查询 tag 标签对象，获取tagId
        Tag tagEntity = tagMapper.selectOne(new LambdaQueryWrapper<Tag>().eq(Tag::getName, tagName));
        List<Post> postList = postMapper.selectByTagId(tagEntity.getTagId() , page,size);

        // 封装结果，属性
        ArrayList<PostVo> postVos = new ArrayList<>();

        for (Post post : postList) {
            postVos.add(this.castPostToPostVo(post));
        }

        return postVos ;
    }
    
    
    
    /**
     * @methodName : 返回系统热门文章，自定义排序字段
     * @author : HK意境
     * @date : 2021/11/26 18:16
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
    public List<Post> getHotPosts(Integer num, Map<String, String> orderCondition) {

        // 构造查询 wrapper
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<Post>()
                //.select(Post::getPostId, Post::getTitle)
                .orderByDesc(Post::getViewNum).orderByDesc(Post::getLikeNum).orderByDesc(Post::getUpdateTime)
                .last("limit "+ num);

        // 构造查询条件：排序条件，阅读，评论，点赞，收藏
        // 对 Map 进行key 值排序
        //orderCondition = new TreeMap<>(orderCondition);
        //ArrayList<String> conditions = new ArrayList<>(orderCondition.values());


        List<Post> postList = postMapper.selectList(wrapper);

        return postList;
    }


    /**
     * @methodName : 获取最新文章
     * @author : HK意境
     * @date : 2021/11/26 20:34
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
    public List<Post> getNewPosts(Integer num, Map orderCondition) {
        // 构造查询 wrapper
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<Post>()
                //.select(Post::getPostId, Post::getTitle)
                .orderByDesc(Post::getUpdateTime).orderByDesc(Post::getCreateTime).orderByDesc(Post::getWeight)
                .last("limit "+ num);

        // 构造查询条件：排序条件，阅读，评论，点赞，收藏
        // 对 Map 进行key 值排序
        //orderCondition = new TreeMap<>(orderCondition);
        //ArrayList<String> conditions = new ArrayList<>(orderCondition.values());

        List<Post> postList = postMapper.selectList(wrapper);

        return postList;
    }



    /**
     * @methodName : 文章按照年月份进行归档
     * @author : HK意境
     * @date : 2021/11/26 20:50
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
    public List<PostArchiveVo> listArchives() {
        List<PostArchiveVo> postArchiveVos = postMapper.selectArchiveList();
        return postArchiveVos ;
    }


    /**
     * @methodName : 转换 Post 列表何 PostVo 列表
     * @author : HK意境
     * @date : 2021/11/26 15:09
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
    public PostVo castPostToPostVo(Post record){

        // 查询文章标签
        List<Tag> tags = tagMapper.selectPostTags(record.getPostId());
        User postAuthor = this.getPostAuthor(record);
        // 封装属性
        PostVo postVo = new PostVo();
        BeanUtils.copyProperties(record, postVo);
        // 设置标签
        postVo.setTagList(tagService.selectByTags(tags));
        // 设置作者信息
        postVo.setAuthor(postAuthor.getUsername()).setAvatarUrl(postAuthor.getAvatar());

        return postVo ;
    }

    /***
     * 集合批处理转换 Post 到 PostVo
     *
     *
     */
    public List<PostVo> castPostToPostVoBatch(List<Post> records, Boolean isAuthor , Boolean isTags, Boolean isCategory){

        List<PostVo> postVos = new ArrayList<>();

        for (Post record : records) {
            // 封装属性
            PostVo postVo = new PostVo();

            if (isTags){
                List<Tag> tags = tagMapper.selectPostTags(record.getPostId());
                // 设置标签
                postVo.setTagList(tagService.selectByTags(tags));
            }
            if (isAuthor){
                User postAuthor = this.getPostAuthor(record);
                // 设置作者信息
                postVo.setAuthor(postAuthor.getUsername()).setAvatarUrl(postAuthor.getAvatar());
            }

            BeanUtils.copyProperties(record, postVo);
            postVos.add(postVo);
        }

        return postVos ;
    }




    /**
     * @methodName : 获取文章作者
     * @author : HK意境
     * @date : 2021/11/26 10:40
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
    public User getPostAuthor(Post post){

        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserId, post.getUserId()));
        return user ;

    }



}




