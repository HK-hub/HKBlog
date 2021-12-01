package com.hkblog.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hkblog.auth.service.LoginService;
import com.hkblog.business.service.impl.UserServiceImpl;
import com.hkblog.common.constant.CodeTypeConstant;
import com.hkblog.common.response.LoginParam;
import com.hkblog.common.response.RegisterParam;
import com.hkblog.common.response.ResponseResult;
import com.hkblog.common.response.ResultCode;
import com.hkblog.common.utils.IdWorker;
import com.hkblog.common.utils.JwtUtils;
import com.hkblog.common.utils.MailUtil;
import com.hkblog.domain.entity.User;
import me.zhyd.oauth.model.AuthResponse;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.beetl.sql.core.query.LambdaQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author : HK意境
 * @ClassName : LoginServiceImpl
 * @date : 2021/12/1 12:34
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private MailerServiceImpl mailerService ;
    @Autowired
    private UserServiceImpl userService ;
    @Autowired
    private RedisTemplate<String, String> redisTemplate ;
    @Autowired
    private IdWorker idWorker ;
    @Autowired
    private JwtUtils jwtUtils ;


    /**
     * @methodName : 邮箱登录
     * @author : HK意境
     * @date : 2021/12/1 12:34
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
    public String emailLogin(String email) {

        // 检查验证码
        String code = redisTemplate.opsForValue().get("emailCode_" + email);
        if (!StringUtils.isEmpty(code)){
            // 验证码未失效
            User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email));
            // 登录成功，返回token
            // 生成 token
            // 构造 claims 数据
            HashMap<String, Object> claimsMap = new HashMap<>();
            claimsMap.put("userId",user.getUserId());
            claimsMap.put("userName",user.getUsername());

            String jwtToken = jwtUtils.createJwtToken(user.getUserId(), user.getUsername() ,claimsMap);

            // redis 检查
            // 将用户 token 添加到 redis 中
            redisTemplate.opsForValue().set("TOKEN_" + jwtToken , JSON.toJSONString(user),3, TimeUnit.HOURS);

            return jwtToken;
        }else{
            return null ;
        }

    }


    // 发送指定类型的验证码
    @Override
    public boolean sendCode(String email, String type) {

        if (type.equals(CodeTypeConstant.EMAIL)){
            // 邮箱验证码
            String code = MailUtil.generateVerCode();
            boolean send = mailerService.send(email, "HKBlog-System 登录验证码",code );
            if (send){
                // 验证码发送成功， 缓存进入redis
                redisTemplate.opsForValue().set("emailCode_"+email , code,5L,TimeUnit.MINUTES);
                return true ;
            }else{
                return false ;
            }
        }else if (type.equals(CodeTypeConstant.MESSAGE)){

        }

        return true ;
    }


    @Override
    public String accountLogin(LoginParam loginParam) {

        // 检查参数
        if (loginParam == null){
            return null ;
        }
        String password = loginParam.getPassword();
        String account = loginParam.getAccount();
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(account)){
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
        redisTemplate.opsForValue().set("TOKEN_" + jwtToken , JSON.toJSONString(user),3, TimeUnit.HOURS);

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
        String redisCache = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isEmpty(redisCache)){
            return true;
        }

        // redis 存在缓存，进行删除
        Boolean delete = redisTemplate.delete("TOKEN_" + token);

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
            redisTemplate.opsForValue().set("TOKEN_" + jwtToken , JSON.toJSONString(newUser),3, TimeUnit.HOURS);

            return new ResponseResult(ResultCode.SUCCESS,jwtToken);
        }

        return new ResponseResult(ResultCode.FAIL, "用户注册失败!未知原因?");
    }



    /**
     * @methodName : 第三方登录，第三方平台登录
     * @author : HK意境
     * @date : 2021/12/1 16:00
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
    public String oAuthLogin(AuthResponse authResponse) {

        JSONObject jsonObject = JSONObject.parseObject(authResponse.getData().toString());
        Boolean save = false ;
        // 获取账号
        String uuid = jsonObject.getString("uuid");
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getAccount, uuid));

        if (user == null){
            // 用户存在，可以进行登录
            user = new User();
            // 设置userId
            user.setUserId(idWorker.nextId()+"");
            // 设置用户名
            String username = jsonObject.getString("username");
            String nickname = jsonObject.getString("nickname");
            if (!StringUtils.isEmpty(username)){
                user.setUsername(username);
            }else if (!StringUtils.isEmpty(nickname)){
                user.setUserId(nickname);
            }
            // 设置账号
            user.setAccount(uuid);
            user.setAvatar(jsonObject.getString("avatar"));
            user.setCreateTime(new Date());
            user.setUpdateTime(user.getCreateTime());
            user.setGithub(username);
            user.setPassword("123456");

            save = userService.save(user);

        }

        // 保存成功
        if (save || user != null){
            // 保存用户成功
            // 生成 token
            // 构造 claims 数据
            HashMap<String, Object> claimsMap = new HashMap<>();
            claimsMap.put("userId",user.getUserId());
            claimsMap.put("userName",user.getUsername());

            String jwtToken = jwtUtils.createJwtToken(user.getUserId(), user.getUsername() ,claimsMap);

            // redis 检查
            // 将用户 token 添加到 redis 中
            redisTemplate.opsForValue().set("TOKEN_" + jwtToken , JSON.toJSONString(user),3, TimeUnit.HOURS);
            return jwtToken;
        }

        return null ;
    }


}

