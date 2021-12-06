package com.hkblog.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkblog.business.mapper.PostMapper;
import com.hkblog.domain.entity.Post;
import com.hkblog.domain.entity.User;
import com.hkblog.domain.entity.UserCollection;
import com.hkblog.business.service.UserCollectionService;
import com.hkblog.business.mapper.UserCollectionMapper;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 *
 */
@Service
public class UserCollectionServiceImpl extends ServiceImpl<UserCollectionMapper, UserCollection>
    implements UserCollectionService{

    @Autowired
    private ThreadService threadService ;
    @Autowired
    private PostMapper postMapper ;
    @Autowired
    private UserCollectionMapper userCollectionMapper ;
    @Autowired
    private RedisTemplate<String, String> redisTemplate ;


    /**
     * @methodName : 用户收藏文章
     * @author : HK意境
     * @date : 2021/12/5 13:56
     * @description :
     * @Todo : 通过 postId ，用户token 收藏文章
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
    public Boolean collectPost(String postId, String token) {

        Post post = postMapper.selectById(postId);
        if (post != null) {
            Boolean b = threadService.updatePostCollectNum(postMapper , post, +1);
        }

        String userJsonString = redisTemplate.opsForValue().get("TOKEN_" + token);
        User user = JSON.parseObject(userJsonString, User.class);

        UserCollection userCollection = userCollectionMapper.selectOne(new LambdaQueryWrapper<UserCollection>()
                .eq(UserCollection::getUserId, user.getUserId())
                .eq(UserCollection::getPostId, postId));

        if (userCollection == null){
            // 用户未收藏，进行收藏
            userCollection = new UserCollection();
            userCollection.setUserId(user.getUserId());
            userCollection.setPostId(postId);
            userCollection.setCreateTime(new Date());

            return userCollectionMapper.insert(userCollection) != 0;
        }
        return  true ;
    }

    /**
     * @methodName : 用户取消收藏文章
     * @author : HK意境
     * @date : 2021/12/5 14:17
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
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteCollectPost(String postId, String token) {
        Post post = postMapper.selectById(postId);
        if (post != null) {
            Boolean b = threadService.updatePostCollectNum(postMapper , post, -1);
        }
        String userJsonString = redisTemplate.opsForValue().get("TOKEN_" + token);
        User user = JSON.parseObject(userJsonString, User.class);

        // 删除
        assert user != null;
        int delete = userCollectionMapper.delete(new LambdaQueryWrapper<UserCollection>()
                .eq(UserCollection::getUserId, user.getUserId())
                .eq(UserCollection::getPostId, postId));

        return delete != 0 ;
    }
}




