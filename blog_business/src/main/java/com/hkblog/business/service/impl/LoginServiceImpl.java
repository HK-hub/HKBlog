package com.hkblog.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.hkblog.business.mapper.UserMapper;
import com.hkblog.business.service.LoginService;
import com.hkblog.common.response.LoginParam;
import com.hkblog.common.response.RegisterParam;
import com.hkblog.common.response.ResponseResult;
import com.hkblog.common.response.ResultCode;
import com.hkblog.common.utils.EncryptUtil;
import com.hkblog.common.utils.IdWorker;
import com.hkblog.common.utils.JwtUtils;
import com.hkblog.domain.entity.User;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author : HK意境
 * @ClassName : LoginServiceImpl
 * @date : 2021/11/27 10:26
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper ;
    @Autowired
    private UserServiceImpl userService ;
    @Autowired
    private IdWorker idWorker ;
    @Autowired
    private JwtUtils jwtUtils ;
    @Autowired
    private EncryptUtil encryptUtil ;
    @Autowired
    private RedisTemplate<String, String> stringRedisTemplate ;

    @Override
    public String accountLogin(LoginParam loginParam) {

        // 检查参数
        if (loginParam == null){
            return null ;
        }
        String password = loginParam.getPassword();
        String account = loginParam.getAccount();
        if (StringUtils.isEmpty(password) || password.isBlank() || StringUtils.isEmpty(account)){
            return null ;
        }

        // 查询用户
        //加密密码
        password = new Md5Hash(password, account, 3).toString();
        //password = encryptUtil.MD5(password, account);

        User user = userService.findUserByAccountPassword(account, password);
        if (user == null){
            return null ;
        }

        // 生成 token
        // 构造 claims 数据
        HashMap<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("userId",user.getUserId());
        claimsMap.put("userName",user.getUsername());

        String jwtToken = jwtUtils.createJwtToken(user.getUserId(), user.getUsername() ,claimsMap);

        // redis 检查
        // 将用户 token 添加到 redis 中
        stringRedisTemplate.opsForValue().set("TOKEN_" + jwtToken , JSON.toJSONString(user),3, TimeUnit.HOURS);

        return jwtToken;
    }


    /**
     * @methodName : 退出登录，携带token 信息头
     * @author : HK意境
     * @date : 2021/11/27 15:14
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
    public boolean logout(String token) {

        // 先查询一下 redis 中是否有缓存，如果没有，说明退出登录成功
        String redisCache = stringRedisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isEmpty(redisCache)){
            return true;
        }

        // redis 存在缓存，进行删除
        Boolean delete = stringRedisTemplate.delete("TOKEN_" + token);

        return delete;
    }


    /**
     * @methodName : 账号，密码注册
     * @author : HK意境
     * @date : 2021/11/27 15:36
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
    @Transactional
    public ResponseResult accountRegister(RegisterParam registerParam) {

        /**
         * 1. 判断参数是否合法
         * 2. 判断账户是否存在
         * 3. 注册用户
         * 4. 生成 token
         * 5. 缓存 redis
         *
         * 整个过程需要使用事务
         */
        String account = registerParam.getAccount();
        String nickname = registerParam.getNickname();
        String password = registerParam.getPassword();

        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)){
            // 参数存在空值
            return new ResponseResult(ResultCode.BAD_REQUEST, "账号,密码参数格式错误!!!");
        }

        User userByAccountPassword = userService.findUserByAccountPassword(account, password);
        if (userByAccountPassword != null) {
            // 账户已经存在
            return new ResponseResult(ResultCode.USER_HAS_EXITS,"账户已经被注册了,请换一个哦!");
        }

        // 设置注册用户属性
        User newUser = new User();
        newUser.setUserId(idWorker.nextId()+"");
        newUser.setUsername(nickname);
        newUser.setPassword(password);
        newUser.setAccount(account);

        // 保存用户
        boolean save = userService.save(newUser);

        // 校验保存结果
        if (save){
            // 保存成功
            // 生成 token
            // 构造 claims 数据
            HashMap<String, Object> claimsMap = new HashMap<>();
            claimsMap.put("userId",newUser.getUserId());
            claimsMap.put("userName",newUser.getUsername());

            String jwtToken = jwtUtils.createJwtToken(newUser.getUserId(), newUser.getUsername() ,claimsMap);

            // redis 检查
            // 将用户 token 添加到 redis 中
            stringRedisTemplate.opsForValue().set("TOKEN_" + jwtToken , JSON.toJSONString(newUser),3, TimeUnit.HOURS);

            return new ResponseResult(ResultCode.SUCCESS,jwtToken);
        }

        return new ResponseResult(ResultCode.FAIL, "用户注册失败!未知原因?");
    }




}
