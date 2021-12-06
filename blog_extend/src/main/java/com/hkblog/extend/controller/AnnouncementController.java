package com.hkblog.extend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.hkblog.common.response.ResponseResult;
import com.hkblog.common.response.ResultCode;
import com.hkblog.common.utils.IdWorker;
import com.hkblog.domain.entity.Announcement;
import com.hkblog.extend.service.impl.AnnouncementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author : HK意境
 * @ClassName : AnnouncementController
 * @date : 2021/12/6 15:55
 * @description : 消息通知
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@CrossOrigin
@RefreshScope
@RestController
@RequestMapping("/announce")
public class AnnouncementController {

    @Autowired
    private IdWorker idWorker ;
    @Autowired
    private AnnouncementServiceImpl announcementService ;

    /**
     * @methodName : 新增网站公告消息
     * @author : HK意境
     * @date : 2021/12/6 15:59
     * @description :
     * @Todo : 发送网站公告消息
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("")
    public ResponseResult publishAnnouncement(@RequestBody Announcement announcement){

        announcement.setId(idWorker.nextId()+"");
        announcement.setCreateTime(new Date());
        announcement.setUpdateTime(announcement.getCreateTime());

        boolean save = announcementService.save(announcement);

        if (save){
            return new ResponseResult(ResultCode.SUCCESS, save);
        }

        return new ResponseResult(ResultCode.FAIL , "发布新公告失败!");
    }



    /**
     * @methodName : 删除公告
     * @author : HK意境
     * @date : 2021/12/6 16:05
     * @description :
     * @Todo : 更具id 删除公告
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

        boolean b = announcementService.removeById(id);
        if (b){
            return new ResponseResult(ResultCode.SUCCESS, b);
        }

        return new ResponseResult(ResultCode.FAIL , "删除公告失败!");

    }




    /**
     * @methodName : 平台撤销公告
     * @author : HK意境
     * @date : 2021/12/6 16:08
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
    @PutMapping("/cancel/{id}")
    public ResponseResult cancelAnnouncement(@PathVariable String id){

        Announcement byId = announcementService.getById(id);
        byId.setCanceled(true);
        byId.setCancelTime(new Date());

        boolean b = announcementService.updateById(byId);

        if (b){
            return new ResponseResult(ResultCode.SUCCESS, b);
        }
        return new ResponseResult(ResultCode.FAIL , "撤销公告失败!");

    }





    /**
     * @methodName : 通过id 查询网站公告
     * @author : HK意境
     * @date : 2021/12/6 16:18
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
    public ResponseResult findById(@PathVariable String id){

        Announcement byId = announcementService.getById(id);

        return new ResponseResult(ResultCode.SUCCESS,byId);
    }



    /**
     * @methodName : 查询全部网站公告, 前台用户
     * @author : HK意境
     * @date : 2021/12/6 16:20
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
    public ResponseResult findList(){

        List<Announcement> list = announcementService.list(new LambdaQueryWrapper<Announcement>()
                .eq(Announcement::getCanceled, false)
                .eq(Announcement::getDeleted, false));

        return new ResponseResult(ResultCode.SUCCESS, list);
    }


    /**
     * @methodName : 通过公告标题,内容查询
     * @author : HK意境
     * @date : 2021/12/6 16:25
     * @description :
     * @Todo : 欲使用 mysql8.0 的全文分词索引查询
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @GetMapping("/keyword")
    public ResponseResult findByTitleAndContent(@RequestBody String keywords){

        List<Announcement> list = announcementService.list(new LambdaQueryWrapper<Announcement>()
                .like(Announcement::getTitle, keywords)
                .or()
                .like(Announcement::getContent, keywords));

        return new ResponseResult(ResultCode.SUCCESS, list) ;

    }



    /**
     * @methodName : 后台系统查询全部网站公告
     * @author : HK意境
     * @date : 2021/12/6 16:23
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
    @GetMapping("/all")
    public ResponseResult getAll(){

        return new ResponseResult(ResultCode.SUCCESS, announcementService.list());

    }




}
