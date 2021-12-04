package com.hkblog.search.dao;

import com.hkblog.domain.entity.Post;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : HK意境
 * @ClassName : PostDao
 * @date : 2021/12/3 9:01
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Repository
public interface PostDao extends ElasticsearchRepository<Post,String> {
}
