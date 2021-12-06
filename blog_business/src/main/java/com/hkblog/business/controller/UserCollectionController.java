package com.hkblog.business.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hkblog.business.service.impl.UserCollectionServiceImpl;
import com.hkblog.common.response.ResponseResult;
import com.hkblog.common.response.ResultCode;
import com.hkblog.domain.entity.User;
import com.hkblog.domain.entity.UserCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author : HK意境
 * @ClassName : UserCollectionController
 * @date : 2021/11/26 0:07
 * @description :
 * @Todo : 解决用户收藏文章的问题
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@RefreshScope
@CrossOrigin
@RestController
@RequestMapping("/collect")
public class UserCollectionController {

    @Autowired
    private UserCollectionServiceImpl userCollectionService ;
    @Autowired
    private RedisTemplate<String,String> redisTemplate ;

    /**
     * @methodName : 用户收藏文章
     * @author : HK意境
     * @date : 2021/12/5 13:53
     * @description :
     * @Todo :
     * @params :
         * @param : postId , userId
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @PostMapping("/{postId}")
    public ResponseResult collectPost(@PathVariable(name = "postId")String postId,
                                      @RequestHeader("Authorization")String token){

        // 跟新收藏数量
        Boolean b = userCollectionService.collectPost(postId, token);
        //
        if (b){
            return ResponseResult.SUCCESS() ;
        }
        return ResponseResult.FAIL() ;
    }



    /**
     * @methodName : 用户取消收藏
     * @author : HK意境
     * @date : 2021/12/5 14:14
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
    @PutMapping("/{postId}")
    public ResponseResult cancelCollect(@PathVariable(name = "postId")String postId,
                                        @RequestHeader("Authorization")String token){

        // 跟新收藏数量
        Boolean b = userCollectionService.deleteCollectPost(postId, token);
        if (b){
            return ResponseResult.SUCCESS();
        }
        return ResponseResult.FAIL();
    }



    /**
     * @methodName : 删除收藏记录
     * @author : HK意境
     * @date : 2021/12/5 14:21
     * @description :
     * @Todo : 后台管理员删除表数据
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseResult deleteCollect(@PathVariable(name = "id")Integer id){
        boolean b = userCollectionService.removeById(id);

        return new ResponseResult(ResultCode.SUCCESS, b) ;

    }



}
