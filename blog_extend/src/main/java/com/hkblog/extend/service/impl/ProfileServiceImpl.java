package com.hkblog.extend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkblog.domain.entity.Profile;
import com.hkblog.extend.service.ProfileService;
import com.hkblog.extend.mapper.ProfileMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class ProfileServiceImpl extends ServiceImpl<ProfileMapper, Profile>
    implements ProfileService{

}




