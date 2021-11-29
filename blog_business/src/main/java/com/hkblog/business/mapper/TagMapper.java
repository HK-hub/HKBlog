package com.hkblog.business.mapper;

import com.hkblog.domain.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author HK意境
 * @Entity com.hkblog.domain.entity.Tag
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * @methodName : 返回博客文章的全部标签
     * @author : HK意境
     * @date : 2021/11/26 10:27
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
    List<Tag> selectPostTags(@Param("postId") String postId);

    /**
     * @methodName : 按文章数据查询热门 tag 标签
     * @author : HK意境
     * @date : 2021/11/26 15:58
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
    List<String> findHotsTag(Integer num);

    /**
     * @methodName : 根据文章ID查询全部文章标签
     * @author : HK意境
     * @date : 2021/11/27 20:50
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
    List<Tag> selectListByPostId(@Param("postId") String postId);
}




