package com.hkblog.business.service;

import com.hkblog.common.response.PageParam;
import com.hkblog.domain.entity.Post;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hkblog.domain.vo.PostArchiveVo;
import com.hkblog.domain.vo.PostVo;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;

/**
 * @ClassName : PostService
 * @author : HK意境
 * @date : 2021/11/26
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public interface PostService extends IService<Post> {


    // 分页显示博客文章
    List<PostVo> listPost(PageParam pageParam);



    // 根据标签查询分页查询文章
    List<PostVo> findByTag(String tag, @Nullable PageParam pageParam);


    // 返回系统热门文章：自定义规则
    List<Post> getHotPosts(Integer num, Map<String, String> orderCondition);


    // 返回最新文章
    List<Post> getNewPosts(Integer num, Map orderCondition);


    // 返回文章按照年月份进行归档
    List<PostArchiveVo> listArchives();
}
