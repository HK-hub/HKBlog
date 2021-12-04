package com.hkblog.search.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author : HK意境
 * @ClassName : SpringDataESIndexTest
 * @date : 2021/12/2 20:07
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SpringDataESIndexTest {

    /*@Autowired
    private ESUserDao esUserDao ;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate ;

    // 创建索引：当我们的程序启动时会自动去读取关联的实体类，如果没有索引，会自动创建索引
    @Test
    public void createIndex(){

        System.out.println("创建索引");
    }

    // 删除索引
    @Test
    public void deleteIndex(){

        boolean b = elasticsearchRestTemplate.deleteIndex(ESUser.class);
        System.out.println(b);

    }*/



}
