package com.hkblog.business.controller;

import com.hkblog.business.service.impl.UserServiceImpl;
import com.hkblog.common.response.ResponseResult;
import com.hkblog.common.response.ResultCode;
import com.hkblog.common.utils.IdWorker;
import com.hkblog.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.Style;
import java.sql.ResultSet;

/**
 * @author : HK意境
 * @ClassName : UserController
 * @date : 2021/11/26 0:04
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@RefreshScope
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private IdWorker idWorker ;

    /**
     * @methodName : 保存用户
     * @author : HK意境
     * @date : 2021/11/26 9:06
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
    @PostMapping()
    public ResponseResult save(@RequestBody User user){

        // 加密密码, 分配ID
        user.setUserId(idWorker.nextId()+"");
        userService.save(user);
        return new ResponseResult(ResultCode.SUCCESS, user) ;
    }


    /**
     * @methodName : 根据id 查询用户
     * @author : HK意境
     * @date : 2021/11/26 9:07
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
    @GetMapping("/{id}")
    public ResponseResult<User> findById(@PathVariable(name = "id")String id){

        User byId = userService.getById(id);
        return new ResponseResult<>(ResultCode.SUCCESS, byId);
    }

    /**
     * @methodName : 修改用户
     * @author : HK意境
     * @date : 2021/11/26 9:07
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
    @PutMapping()
    public ResponseResult<User> updateUser(@RequestBody User user){

        boolean res = userService.saveOrUpdate(user);
        ResponseResult<User> result = new ResponseResult<>(ResultCode.SUCCESS, user);
        if (res == true){
            result.setResultCode(ResultCode.SUCCESS);
        }else{
            result.setResultCode(ResultCode.FAIL);
        }

        return result ;
    }


    /**
     * @methodName : 根据Id删除用户
     * @author : HK意境
     * @date : 2021/11/26 9:10
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
    @DeleteMapping("/{id}")
    public ResponseResult deleteById(@PathVariable(name = "id")String id){

        boolean res = userService.removeById(id);
        if (res == true){
            return ResponseResult.SUCCESS();
        }else{
            return ResponseResult.FAIL();
        }
    }


    /**
     * @methodName : 更新用户头像
     * @author : HK意境
     * @date : 2021/11/26 15:41
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
    @RequestMapping(value = "/avatar", method = RequestMethod.POST)
    public ResponseResult avatar(@RequestPart("file") MultipartFile file){


        return new ResponseResult(ResultCode.SUCCESS);
    }


    /**
     * @methodName : 获取用户信息 ，通过 token 获取信息
     * @author : HK意境
     * @date : 2021/11/27 13:24
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
    @GetMapping("/info")
    public ResponseResult getUserInfo(@RequestHeader("Authorization")String authorization){

        // 获取请求头中的 token 信息, 或者使用注解 @RequestHeader()
        //String authorization = request.getHeader("Authorization");

        // 获取userId ，查询用户
        User user = userService.findUserByToken(authorization);
        if (user == null){
            // token 为空，token 不合法，token 过期
            return new ResponseResult(ResultCode.TOKEN_ERROR , "用户未登录或登录过期,请重新登录");
        }

        return new ResponseResult(ResultCode.SUCCESS,user);
    }



}
