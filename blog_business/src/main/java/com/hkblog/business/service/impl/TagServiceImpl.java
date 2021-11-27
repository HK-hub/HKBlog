package com.hkblog.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkblog.domain.entity.Tag;
import com.hkblog.business.service.TagService;
import com.hkblog.business.mapper.TagMapper;
import com.hkblog.domain.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

    @Autowired
    private TagMapper tagMapper ;

    @Override
    public TagVo selectByTag(Tag tag) {
        TagVo tagVo = new TagVo(tag);

        return tagVo ;
    }


    /**
     * @methodName : 查找标签下的所有文章
     * @author : HK意境
     * @date : 2021/11/26 14:52
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
    public List<TagVo> selectByTags(List<Tag> tags) {

        List<TagVo> tagVos = new ArrayList<>();
        for (Tag tag : tags) {
            tagVos.add(new TagVo(tag));
        }
        return tagVos;
    }

    
    /**
     * @methodName : 查询指定数量的热门标签
     * @author : HK意境
     * @date : 2021/11/26 15:52
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
    public List<Tag> hotsTag(Integer num) {

        List<String> tagIds =  tagMapper.findHotsTag(num);

        // 批量查询 tag 标签
        List<Tag> tagList = tagMapper.selectBatchIds(tagIds);
        return tagList;
    }


}




