package com.hkblog.business.controller;

import com.hkblog.business.service.impl.PostServiceImpl;
import com.hkblog.common.response.PageParam;
import com.hkblog.common.response.ResponseResult;
import com.hkblog.common.response.ResultCode;
import com.hkblog.common.utils.IdWorker;
import com.hkblog.domain.entity.Post;
import com.hkblog.domain.vo.PostArchiveVo;
import com.hkblog.domain.vo.PostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@RefreshScope
@CrossOrigin
@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostServiceImpl postService;
    @Autowired
    private IdWorker idWorker;

    /**
     * @methodName : 保存Post文章
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
    @PostMapping("/pages")
    public ResponseResult listPost(@RequestBody PageParam pageParam){

        // 查询分页博客文章
        List<PostVo> voList = postService.listPost(pageParam);

        //new PageResult<>()
        return new ResponseResult<>(ResultCode.SUCCESS,voList);
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




}
