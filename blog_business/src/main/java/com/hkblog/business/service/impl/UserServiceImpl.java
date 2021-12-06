package com.hkblog.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkblog.common.utils.EncryptUtil;
import com.hkblog.common.utils.JwtUtils;
import com.hkblog.domain.entity.User;
import com.hkblog.business.service.UserService;
import com.hkblog.business.mapper.UserMapper;
import com.hkblog.domain.vo.UserVo;
import io.jsonwebtoken.Claims;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper ;
    @Autowired
    private PostServiceImpl postService;
    @Autowired
    private EncryptUtil encryptUtil ;
    @Autowired
    private JwtUtils jwtUtils ;
    @Autowired
    private RedisTemplate<String, String> stringRedisTemplate;

    /**
     * @methodName : 根据账号名，密码查询用户
     * @author : HK意境
     * @date : 2021/11/27 10:44
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
    @Override
    public User findUserByAccountPassword(String account, String password) {

        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getAccount, account)
                .eq(User::getPassword, password));

        return user;
    }

    @Trace
    @Override
    public User findUserByToken(String token) {

        // 验证token 合法信息
        if (StringUtils.isEmpty(token)){
            return null ;
        }
        // 解析 token
        Claims claims = jwtUtils.parseJwtToken(token);

        // JwtUtils 检验
        if (claims == null || claims.isEmpty()){
            // 校验失败
            return null;
        }

        // redis 解析是否成功
        String userJson = stringRedisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isEmpty(userJson)){
            // 过期 token
            return null;
        }

        // 解析userJson 字符串变换为 user 对象
        User user = JSON.parseObject(userJson, User.class);

        return user;
    }


    /**
     * @methodName : 通过作者ID 获取到作者的前端展示信息
     * @author : HK意境
     * @date : 2021/11/28 14:31
     * @description :
     * @Todo : 获取user 封装 author
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
    public UserVo getUserVo(String userId) {

        User user = userMapper.selectById(userId);

        return new UserVo(user);
    }


    /**
     * @methodName : 通过文章id 获取用户信息
     * @author : HK意境
     * @date : 2021/11/28 15:32
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
    @Override
    public User findUserByPostId(String postId) {
        User author = postService.getPostAuthorById(postId);
        return author;
    }


    /**
     * @methodName : 重写保存用户操作，对密码进行加密
     * @author : HK意境
     * @date : 2021/11/27 11:17
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
    @Override
    public boolean save(User entity) {

        //String password = encryptUtil.MD5(entity.getPassword(), entity.getAccount());
        String password = new Md5Hash(entity.getPassword(), entity.getAccount(), 3).toString();

        //加密密码
        entity.setPassword(password);

        // 设置时间
        entity.setUpdateTime(new Date());
        entity.setCreateTime(entity.getUpdateTime());

        return super.save(entity);
    }





}




