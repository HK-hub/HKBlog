package com.hkblog.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkblog.business.mapper.CategoryMapper;
import com.hkblog.business.mapper.TagMapper;
import com.hkblog.business.mapper.UserMapper;

import com.hkblog.common.response.PageParam;
import com.hkblog.common.response.PostParam;
import com.hkblog.common.utils.IdWorker;
import com.hkblog.domain.entity.Category;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
    private CategoryServiceImpl categoryService;
    @Autowired
    private UserMapper userMapper ;
    @Autowired
    private TagServiceImpl tagService;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private ThreadService threadService ;

    public static final String cover = "http://img.netbian.com/file/2021/0810/d33c96a71890de84c2bdc4847a1aeaf2.jpg" ;


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

        Page<Post> postPage = postMapper.selectPage(new Page<Post>(pageParam.getPage(), pageParam.getPageSize()),
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
            // 查询文章分类
            List<Category> categoryList = categoryService.findCategoryListByPostId(record.getPostId());
            User postAuthor = this.getPostAuthor(record);
            // 封装属性
            PostVo postVo = new PostVo();
            BeanUtils.copyProperties(record, postVo);
            // 设置标签
            postVo.setTagList(tagService.selectByTags(tags));
            // 设置分类
            postVo.setCategoryList(categoryList);
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
     * @methodName : 查询文章详情，VO 对象，前端阅读博客文章
     * @author : HK意境
     * @date : 2021/11/27 20:31
     * @description :
     * @Todo : 标签列表，分类列表
     * @params : 
         * @param : null 
     * @return : null
     * @throws: 
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @Override
    public PostVo getPostDetailVoById(String postId) {

        // 查询文章
        Post post = postMapper.selectById(postId);

        // 封装属性
        PostVo postVo = this.castPostToPostVo(post);

        /***
         * 查看完文章之后，阅读数量因该增加？会存在问题————
         *      查看完文章之后，本应该直接返回数据了，这时候做了一个更新操作，更新时 加写锁，阻塞其他的读写操作，性能会比较低
         *      更新增加了接口的耗时，如果一旦跟新出问题了，不能影响到查看文章的操作
         *
         * 其中一种解决方案：
         *      使用线程池技术：把所有更新操作扔到线程池去执行，和主线程不相关
         *
         */
        Boolean res = threadService.updatePostViewNum(postMapper,post);

        return postVo;
    }





    /**
     * @methodName : 通过文章id 获取文章信息
     * @author : HK意境
     * @date : 2021/11/28 15:35
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
    public User getPostAuthorById(String postId) {
        System.out.println("postId: "+ postId);
        String userId = postMapper.selectById(postId).getUserId();
        return userMapper.selectById(userId);
    }




    /**
     * @methodName : 跟新文章的评论数量
     * @author : HK意境
     * @date : 2021/11/28 16:08
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
    public Boolean updatePostCommentNum(String postId) {

        // 更新评论数量
        // 使用线程池去更新评论数量
        Boolean aBoolean = threadService.updatePostCommentNum(postMapper, postMapper.selectById(postId));

        return aBoolean;
    }



    /**
     * @methodName : 新增博客文章
     * @author : HK意境
     * @date : 2021/11/28 21:33
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
    @Transactional
    @Override
    public Post saveNewPost(PostParam postParam,String authorId) {

        Post post = new Post();
        post.setPostId(idWorker.nextId()+"");
        post.setTitle(postParam.getTitle());
        // 设置文章 markdown 内容
        post.setContent(postParam.getBody().getContent());
        // 文章封面处理：
        if (StringUtils.isEmpty(postParam.getCoverUrl())){
            postParam.setCoverUrl(cover);
        }
        post.setCoverUrl(postParam.getCoverUrl());

        post.setDescription(postParam.getDescription());
        post.setCreateTime(new Date());
        post.setUpdateTime(post.getCreateTime());

        post.setUserId(authorId);

        // 保存文章
        int insert = postMapper.insert(post);

        return post;
    }



    /**
     * @methodName : 通过文章分类id ,查找该分类下的全部文章信息
     * @author : HK意境
     * @date : 2021/11/29 19:59
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
    public List<PostVo> findAllPostByCategory(String categoryId) {

        List<Post> postList = postMapper.selectListByCategory(categoryId);

        return this.castPostToPostVoBatch(postList,true ,true,true);
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
        // 获得全部分类信息
        List<Category> categories = categoryService.findCategoryListByPostId(record.getPostId());
        User postAuthor = this.getPostAuthor(record);
        // 封装属性
        PostVo postVo = new PostVo();
        BeanUtils.copyProperties(record, postVo);
        // 设置标签
        postVo.setTagList(tagService.selectByTags(tags));
        // 设置分类
        postVo.setCategoryList(categories);
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

            if (isCategory){
                // 设置标签
                List<Category> categoryList = categoryService.findCategoryListByPostId(record.getPostId());
                postVo.setCategoryList(categoryList);
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




