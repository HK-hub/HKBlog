package com.hkblog.business.controller;

import com.hkblog.business.service.impl.TagServiceImpl;
import com.hkblog.common.response.ResponseResult;
import com.hkblog.common.response.ResultCode;
import com.hkblog.common.utils.IdWorker;
import com.hkblog.domain.entity.Tag;
import com.hkblog.domain.vo.TagVo;
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
     * @methodName : 通过id 获取标签的详细信息，前台用户系统展示
     * @author : HK意境
     * @date : 2021/11/30 14:38
     * @description :
     * @Todo : 通过标签id 查询标签vo 对象
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @GetMapping("/detail/{id}")
    public ResponseResult<TagVo> getTagDetailById(@PathVariable(name = "id")String tagId){

        TagVo tagVo = tagService.selectTagDetailById(tagId);
        return new ResponseResult<>(ResultCode.SUCCESS , tagVo) ;
    }



    /**
     * @methodName : 获取全部标签，后台展示
     * @author : HK意境
     * @date : 2021/11/30 14:38
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
    public ResponseResult<List<Tag>> all(){

        List<Tag> list = tagService.list(null);
        return new ResponseResult<>(ResultCode.SUCCESS ,list);
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



    /**
     * @methodName : 获取全部标签Vo 对象
     * @author : HK意境
     * @date : 2021/11/28 21:14
     * @description :
     * @Todo : 获取全部的标签TagVo
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @GetMapping("/all/vo")
    public ResponseResult findAllTagVos(){

        List<TagVo> tagVos = tagService.selectByTags(tagService.list());

        return new ResponseResult(ResultCode.SUCCESS, tagVos);
    }



    /**
     * @methodName : 查询全部标签的详细信息
     * @author : HK意境
     * @date : 2021/11/29 17:00
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
    @GetMapping("/all/detail")
    public ResponseResult findAllTagDetail(){

        List<TagVo> tagVos = tagService.selectByTags(tagService.list());

        return new ResponseResult(ResultCode.SUCCESS, tagVos);
    }


}
