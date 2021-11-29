package com.hkblog.business.service;

import com.hkblog.domain.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hkblog.domain.vo.TagVo;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 *
 */
public interface TagService extends IService<Tag> {

    /**
     * @methodName : 返回 tag 标签的 vo  对象
     * @author : HK意境
     * @date : 2021/11/26 10:46
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
    public TagVo selectByTag(Tag tag);

    public List<TagVo> selectByTags(List<Tag> tags);


    /**
     * @methodName : 查询最热标签
     * @author : HK意境
     * @date : 2021/11/26 15:49
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
    List<Tag> hotsTag(@Nullable Integer num);


    /**
     * @methodName : 根据文章ID 查询全部标签
     * @author : HK意境
     * @date : 2021/11/27 20:47
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
    List<Tag> findTagListByPostId(String postId);
}
