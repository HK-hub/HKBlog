package com.hkblog.business.controller;

import com.hkblog.business.service.impl.UserServiceImpl;
import com.hkblog.common.response.ResponseResult;
import com.hkblog.common.response.ResultCode;
import com.hkblog.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


}
