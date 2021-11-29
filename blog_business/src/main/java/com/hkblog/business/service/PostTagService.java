package com.hkblog.business.service;

import com.hkblog.common.response.PostParam;
import com.hkblog.domain.entity.PostTag;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface PostTagService extends IService<PostTag> {

    // 新增文章，保存文章标签列表
    Boolean savePostTagList(PostParam postParam);
}
