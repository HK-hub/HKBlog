package com.hkblog.extend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hkblog.common.response.ResponseResult;
import com.hkblog.common.response.ResultCode;
import com.hkblog.common.utils.IdWorker;
import com.hkblog.domain.entity.Friend;
import com.hkblog.extend.service.FriendService;
import com.hkblog.extend.service.impl.FriendServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author : HK意境
 * @ClassName : FriendController
 * @date : 2021/12/4 18:26
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@CrossOrigin
@RefreshScope
@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendServiceImpl friendService ;
    @Autowired
    private IdWorker idWorker ;


    /**
     * @methodName : 添加友链
     * @author : HK意境
     * @date : 2021/12/4 19:52
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
    //@Transactional(rollbackFor = Exception.class)
    @PostMapping
    public ResponseResult save(@RequestBody Friend friend){

        friend.setId(idWorker.nextId());
        friend.setCreateTime(new Date());
        friend.setUpdateTime(friend.getCreateTime());
        boolean save = friendService.save(friend);
        return new ResponseResult(ResultCode.SUCCESS, save) ;
    }


    /**
     * @methodName : 获取友链推荐
     * @author : HK意境
     * @date : 2021/12/4 19:47
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
    @GetMapping("/list")
    public ResponseResult getFriendList(@RequestParam(name = "num", required = false , defaultValue = "7")Integer num){

        List<Friend> list = friendService.list(new LambdaQueryWrapper<Friend>()
                .orderByDesc(Friend::getViews)
                .last("limit " + num));

        return new ResponseResult(ResultCode.SUCCESS , list);
    }


    // 删除友站
    @DeleteMapping("/{id}")
    public ResponseResult deleteFriend(@PathVariable(name = "id")String id){

        boolean b = friendService.removeById(id);

        return new ResponseResult(ResultCode.SUCCESS, b) ;
    }


    // 友站点击次数统计
    @PutMapping("/count/{id}")
    public ResponseResult friendCount(@PathVariable Integer id){

        Friend byId = friendService.getById(id);
        byId.setViews(byId.getViews() + 1);
        boolean b = friendService.updateById(byId);

        return new ResponseResult(ResultCode.SUCCESS ,b) ;
    }


}
