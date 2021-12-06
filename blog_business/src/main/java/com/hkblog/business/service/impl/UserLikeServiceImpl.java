package com.hkblog.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkblog.domain.entity.User;
import com.hkblog.domain.entity.UserLike;
import com.hkblog.business.service.UserLikeService;
import com.hkblog.business.mapper.UserLikeMapper;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class UserLikeServiceImpl extends ServiceImpl<UserLikeMapper, UserLike>
    implements UserLikeService{

    @Autowired
    private RedisTemplate<String, String> redisTemplate ;
    @Autowired
    private UserLikeMapper userLikeMapper ;
    /**
     * @methodName : 根据文章id, 用户删除用户喜欢的文章
     * @author : HK意境
     * @date : 2021/12/5 13:35
     * @description :
     * @Todo : postId, userId
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @Trace
    @Override
    public Boolean deleteUserLike(String id, String token) {
        // 获取用户
        String userJsonString = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (userJsonString != null) {
            // 用户存在
            User user = JSON.parseObject(userJsonString, User.class);
            if (user != null){
                int delete = userLikeMapper.delete(new LambdaQueryWrapper<UserLike>()
                        // 用户
                        .eq(UserLike::getUserId, user.getUserId())
                        // 文章
                        .eq(UserLike::getPostId, id));
                return delete != 0 ;
            }
        }
        return false ;
    }
}




