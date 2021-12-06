package com.hkblog.business.service;

import com.hkblog.domain.entity.UserCollection;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface UserCollectionService extends IService<UserCollection> {


    // 用户收藏文章
    Boolean collectPost(String postId, String token);


    // 用户取消收藏文章
    Boolean deleteCollectPost(String postId, String token);
}
