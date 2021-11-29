package com.hkblog.business.service;

import com.hkblog.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hkblog.domain.vo.UserVo;

/**
 *
 */
public interface UserService extends IService<User> {


    // 根据账号密码查询用户
    User findUserByAccountPassword(String account, String password);

    // 通过 token 信息获取user
    User findUserByToken(String authorization);


    // 获取文章作者信息
    UserVo getUserVo(String userId);

    // 通过文章id 查询作者信息
    User findUserByPostId(String postId);
}
