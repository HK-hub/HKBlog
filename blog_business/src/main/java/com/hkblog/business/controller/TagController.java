package com.hkblog.business.controller;

import com.hkblog.business.service.impl.TagServiceImpl;
import com.hkblog.common.response.ResponseResult;
import com.hkblog.common.response.ResultCode;
import com.hkblog.common.utils.IdWorker;
import com.hkblog.domain.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : HK意境
 * @ClassName : TagController
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
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagServiceImpl tagService;
    @Autowired
    private IdWorker idWorker ;


    /**
     * 保存标签
     *
     */
    @PostMapping()
    public ResponseResult<Tag> save(@RequestBody Tag tag){
        tag.setTagId(idWorker.nextId() + "");
        tagService.save(tag) ;
        return new ResponseResult<>(ResultCode.SUCCESS,tag) ;

    }



    /**
     * @methodName : 最热标签
     * @author : HK意境
     * @date : 2021/11/26 15:44
     * @description :
     * @Todo : 查询热门标签，在列表展示
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @GetMapping("/hot")
    public ResponseResult hotsTags(@RequestParam(value = "num",required = false, defaultValue = "6")Integer num){

        List<Tag> tags = tagService.hotsTag(num);
        return new ResponseResult(ResultCode.SUCCESS,tags);
    }


}
