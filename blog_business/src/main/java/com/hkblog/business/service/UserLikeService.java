package com.hkblog.business.service;

import com.hkblog.domain.entity.UserLike;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface UserLikeService extends IService<UserLike> {


    // 删除用户喜欢的文章
    Boolean deleteUserLike(String id, String token);
}
