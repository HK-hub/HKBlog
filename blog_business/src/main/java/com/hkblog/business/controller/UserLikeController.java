package com.hkblog.business.controller;

import com.alibaba.fastjson.JSON;
import com.hkblog.business.service.PostService;
import com.hkblog.business.service.impl.PostServiceImpl;
import com.hkblog.business.service.impl.ThreadService;
import com.hkblog.business.service.impl.UserLikeServiceImpl;
import com.hkblog.common.response.ResponseResult;
import com.hkblog.common.response.ResultCode;
import com.hkblog.common.utils.JwtUtils;
import com.hkblog.domain.entity.Post;
import com.hkblog.domain.entity.User;
import com.hkblog.domain.entity.UserLike;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author : HK意境
 * @ClassName : UserLikeController
 * @date : 2021/11/26 0:07
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@RefreshScope
@CrossOrigin
@RestController
@RequestMapping("/like")
public class UserLikeController {

    @Autowired
    private JwtUtils jwtUtils ;
    @Autowired
    private ThreadService threadService ;
    @Autowired
    private PostServiceImpl postService ;
    @Autowired
    private UserLikeServiceImpl userLikeService ;
    @Autowired
    private RedisTemplate<String, String> redisTemplate ;

    /**
     * @methodName : 用户点击喜欢文章
     * @author : HK意境
     * @date : 2021/12/5 11:37
     * @description :
     * @Todo : 保存用户点赞的文章
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/{postId}")
    public ResponseResult likePost(@PathVariable("postId")String id,
                                   @RequestHeader(value = "Authorization" , required = false)String token){

        // 更新次数
        Boolean b = postService.updatePostLikeNum(id);

        if (StringUtils.isEmpty(token)){
            // 用户校验失败
            return new ResponseResult(ResultCode.UNAUTHORIZED , "您还未登录,请先登录!");
        }

        // 更新用户点赞文章
        UserLike userLike = new UserLike();

        // 获取用户
        String userJsonString = redisTemplate.opsForValue().get("TOKEN_" + token);
        User user = JSON.parseObject(userJsonString, User.class);

        userLike.setUserId(user.getUserId());
        userLike.setPostId(id);
        userLike.setCreateTime(new Date());
        userLike.setUpdateTime(userLike.getCreateTime());

        userLikeService.save(userLike);

        return new ResponseResult(ResultCode.SUCCESS , b);
    }



    /**
     * @methodName : 用户取消点赞文章
     * @author : HK意境
     * @date : 2021/12/5 13:29
     * @description :
     * @Todo : 取消点赞，删除用户点赞的文章,
     * @params :
         * @param : postId 文章id
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @DeleteMapping("/cancel/{postId}")
    public ResponseResult deleteLikePost(@PathVariable(name = "postId")String id,
                                         @RequestHeader("Authorization")String token){

        if (StringUtils.isEmpty(token)){
            return new ResponseResult(ResultCode.UNAUTHORIZED,"未登录!");
        }

        // 删除喜欢
        Boolean b = userLikeService.deleteUserLike(id, token);
        return new ResponseResult(ResultCode.SUCCESS,b);

    }



    // 通过 userLike 实体id 删除关系
    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable Integer id){

        boolean b = userLikeService.removeById(id);

        if (b) {
            // 删除成功
            return ResponseResult.SUCCESS();
        }
        return ResponseResult.FAIL() ;
    }




}
