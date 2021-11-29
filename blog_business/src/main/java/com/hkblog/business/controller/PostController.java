package com.hkblog.business.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hkblog.business.service.impl.*;
import com.hkblog.common.exception.CommonException;
import com.hkblog.common.log.Log4j;
import com.hkblog.common.response.PageParam;
import com.hkblog.common.response.PostParam;
import com.hkblog.common.response.ResponseResult;
import com.hkblog.common.response.ResultCode;
import com.hkblog.common.utils.IdWorker;
import com.hkblog.common.utils.JwtUtils;
import com.hkblog.domain.entity.Category;
import com.hkblog.domain.entity.Post;
import com.hkblog.domain.vo.PostArchiveVo;
import com.hkblog.domain.vo.PostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : HK意境
 * @ClassName : PostController
 * @date : 2021/11/26 0:05
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostServiceImpl postService;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private ThreadService threadService ;
    @Autowired
    private JwtUtils jwtUtils ;
    @Autowired
    private CategoryServiceImpl categoryService ;
    @Autowired
    private PostCategoryServiceImpl postCategoryService ;
    @Autowired
    private PostTagServiceImpl postTagService ;



    /**
     * @methodName : 保存Post文章, 后台版本
     * @author : HK意境
     * @date : 2021/11/26 0:39
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
    @PostMapping("/new")
    public ResponseEntity save(@RequestBody Post post){
        post.setPostId(idWorker.nextId()+"");
        postService.save(post);
        return new ResponseEntity(post, HttpStatus.OK);
    }


    /**
     * @methodName : 发布新文章，前台用户发布新的博客
     * @author : HK意境
     * @date : 2021/11/28 21:30
     * @description :
     * @Todo : 用户发布新博客文章
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @Transactional
    @PostMapping("/publish")
    public ResponseResult publishPost(@RequestBody PostParam postParam, @RequestHeader("Authorization")String token) throws CommonException, JsonProcessingException {

        try{
            // 提取 authorId
            String authorId = jwtUtils.parseJwtToken(token).getId();

            // 保存新文章
            Post post = postService.saveNewPost(postParam,authorId);

            // 保存文章分类
            postParam.setId(post.getPostId());
            Boolean aBoolean = postCategoryService.savePostCategory(postParam);

            // 保存文章标签列表
            Boolean b = postTagService.savePostTagList(postParam);

            // 发布文章成功
            return new ResponseResult(ResultCode.SUCCESS,post);

        }catch (Exception e){
            throw new CommonException(ResultCode.SERVER_BUSY);
        }

    }



    /**
     * @methodName : 后台系统接口
     * @author : HK意境
     * @date : 2021/11/26 9:37
     * @description : 查询全部文章列表
     * @Todo :
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @GetMapping("/all")
    public ResponseEntity allPosts(){
        List<Post> list = postService.list();
        return new ResponseEntity(list,HttpStatus.OK);
    }


    /**
     * @methodName : 前台文章系统———— 分页查询文章
     * @author : HK意境
     * @date : 2021/11/26 9:38
     * @description :
     * @Todo : 前端传递分页参数
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @Log4j(module = "博客文章", operation = "查询前端用户界面首页文章列表")
    @PostMapping("/pages")
    public ResponseResult listPost(@RequestBody PageParam pageParam){

        // 查询分页博客文章
        List<PostVo> voList = postService.listPost(pageParam);

        // 去除不需要的分类文章，不需要的分类标签
        if (!StringUtils.isEmpty(pageParam.getCategoryId())){
            voList = this.selectListByCategory(pageParam.getCategoryId(), voList);
        }
        //new PageResult<>()
        /*System.out.println("=============================================================");
        for (PostVo postVo : voList) {
            System.out.println(postVo.getCategoryList().size());
        }*/
        return new ResponseResult<>(ResultCode.SUCCESS,voList);
    }

    public List<PostVo> selectListByCategory(String categoryId,List<PostVo> voList){

        List<PostVo> postByCT = new ArrayList<PostVo>();

        if (!StringUtils.isEmpty(categoryId)){
            // 存在文章分类筛选条件
            for (int i = 0; i < voList.size(); i++) {
                PostVo postVo = voList.get(i);

                // 获取vo 的分类信息
                List<Category> categoryList = postVo.getCategoryList();
                boolean flag = false ;
                for (Category category : categoryList) {
                    if (categoryId.equals(category.getId())){
                        // 查询分类id 存在
                        flag = true ;
                        break;
                    }
                }
                if (flag){
                    // 符合文章分类,添加到列表中
                    postByCT.add(postVo);
                }
            }

        }
        return postByCT ;
    }


    /**
     * @methodName : 更具 tag 标签查找全部文章
     * @author : HK意境
     * @date : 2021/11/26 14:52
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
    @RequestMapping(value = "/{tag}",method = RequestMethod.GET)
    public ResponseResult findByTag(@PathVariable(name = "tag",required = true)String tag,
                                    @RequestParam(name = "page", required = false, defaultValue = "1")Integer page,
                                    @RequestParam(name = "size", required = false,defaultValue = "10")Integer size){

        PageParam pageParam = new PageParam(page, size);

        List<PostVo> postVoList= postService.findByTag(tag,pageParam);

        return new ResponseResult(ResultCode.SUCCESS , postVoList);
    }



    /**
     * @methodName : 更具文章分类查找全部文章信息
     * @author : HK意境
     * @date : 2021/11/29 19:55
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
    @RequestMapping(value = "/category/detail/{id}", method = RequestMethod.GET)
    public ResponseResult findDetailByCategory(@PathVariable(name = "id")String categoryId){

        List<PostVo> postVoList = postService.findAllPostByCategory(categoryId);

        return new ResponseResult(ResultCode.SUCCESS, postVoList);
    }

    /**
     * @methodName : 最热文章
     * @author : HK意境
     * @date : 2021/11/26 18:09
     * @description :
     * @Todo : 返回最热文章
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @Log4j(module = "博客文章", operation = "查询前端用户界面首页侧边栏最热文章")
    @GetMapping("/hot")
    public ResponseResult hotPosts(@RequestParam(name = "num", required = false ,defaultValue = "10")Integer num,
                                   @RequestParam(required = false) Map orderCondition){

        if (num == null){
            num = 10 ;
        }
        // 处理 Map 排序条件
        if (orderCondition != null || orderCondition.isEmpty()) {
            orderCondition.put("1","viewNum");
        }

        List<Post> hotPostList = postService.getHotPosts(num, orderCondition) ;
        List<PostVo> voHotList = postService.castPostToPostVoBatch(hotPostList, false, false, false);

        return new ResponseResult(ResultCode.SUCCESS, voHotList);
    }




    /**
     * @methodName : 首页最新文章
     * @author : HK意境
     * @date : 2021/11/26 18:09
     * @description :
     * @Todo : 返回最热文章
     * @params :
     * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @GetMapping("/new")
    public ResponseResult newPosts(@RequestParam(name = "num", required = false ,defaultValue = "5")Integer num,
                                   @RequestParam(required = false) Map orderCondition){

        if (num == null){
            num = 10 ;
        }
        // 处理 Map 排序条件
        if (orderCondition != null || orderCondition.isEmpty()) {
            orderCondition.put("1","updateTime");
        }

        List<Post> hotPostList = postService.getNewPosts(num, orderCondition) ;
        List<PostVo> voHotList = postService.castPostToPostVoBatch(hotPostList, false, false, false);

        return new ResponseResult(ResultCode.SUCCESS, voHotList);
    }

    
    
    
    /**
     * @methodName : 文章归档
     * @author : HK意境
     * @date : 2021/11/26 20:44
     * @description : 文章按照年月日进行归档
     * @Todo :
     * @params : 
         * @param : null 
     * @return : null
     * @throws: 
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @GetMapping("/archives")
    public ResponseResult postArchives(){

        List<PostArchiveVo> postArchiveVos = postService.listArchives();

        return new ResponseResult(ResultCode.SUCCESS, postArchiveVos);
    }

    
    
    /**
     * @methodName : 文章阅读，文章详情
     * @author : HK意境
     * @date : 2021/11/27 20:24
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
    @GetMapping("/view/{id}")
    public ResponseResult viewPost(@PathVariable(name = "id")String id){

        PostVo vo = postService.getPostDetailVoById(id);

        /***
         * 查看完文章之后，阅读数量因该增加？会存在问题————
         *      查看完文章之后，本应该直接返回数据了，这时候做了一个更新操作，更新时 加写锁，阻塞其他的读写操作，性能会比较低
         *      更新增加了接口的耗时，如果一旦跟新出问题了，不能影响到查看文章的操作
         * 
         * 其中一种解决方案：
         *      使用线程池技术：把所有更新操作扔到线程池去执行，和主线程不相关
         * 
         */

        return new ResponseResult(ResultCode.SUCCESS,vo);
    }






}
