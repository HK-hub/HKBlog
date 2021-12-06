package com.hkblog.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkblog.common.response.PostParam;
import com.hkblog.common.utils.IdWorker;
import com.hkblog.domain.entity.PostTag;
import com.hkblog.business.service.PostTagService;
import com.hkblog.business.mapper.PostTagMapper;
import com.hkblog.domain.entity.Tag;
import com.hkblog.domain.vo.TagVo;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
public class PostTagServiceImpl extends ServiceImpl<PostTagMapper, PostTag>
    implements PostTagService{

    @Autowired
    private IdWorker idWorker ;
    @Autowired
    private PostTagMapper postTagMapper ;

    /**
     * @methodName : 保存新增文章标签列表
     * @author : HK意境
     * @date : 2021/11/29 9:46
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
    @Transactional
    @Override
    @Trace
    public Boolean savePostTagList(PostParam postParam) {

        // 获取标签列表
        List<TagVo> tagVos = postParam.getTags();

        int res = 0 ;

        for (TagVo tagVo : tagVos) {

            // 设置信息
            PostTag postTag = new PostTag();
            postTag.setTagId(tagVo.getId());
            postTag.setPostId(postParam.getId());
            postTag.setCreateTime(new Date());
            postTag.setUpdateTime(postTag.getCreateTime());

            // 保存
             res += postTagMapper.insert(postTag);

        }

        return res != 0 ;
    }
}




