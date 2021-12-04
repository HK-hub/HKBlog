package com.hkblog.search.test;


import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;

/**
 * @author : HK意境
 * @ClassName : ESDocTest
 * @date : 2021/12/2 10:44
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class ESDocTest {

    private RestHighLevelClient client = null ;

    @BeforeAll
    public void init(){
        // 创建ES客户端
        client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );
    }


    // 插入数据
    @Test
    public void insertDataToIndex() throws IOException {

        // 设置需要操作的索引
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.index("user").id("1003");

        // 准备数据
        /*ESUser esUser = new ESUser();
        esUser.setUsername("HK");
        esUser.setAge(18);
        esUser.setId("111111111111");
        esUser.setPassword("1234456");*/

        //String jsonString = JSONObject.toJSONString(esUser);

        // 将对象转换为 JSON 字符串,向ES插入数据，必须将数据转化为JSON格式
        //indexRequest.source(jsonString,XContentType.JSON);

        //IndexResponse index = client.index(indexRequest, RequestOptions.DEFAULT);

    }

    // 修改数据
    @Test
    public void updateDataToIndex() throws IOException {

        // 设置需要操作的索引
        UpdateRequest request = new UpdateRequest();
        request.index("user").id("1003");
        request.doc(XContentType.JSON,"username","意境");



        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);



    }



    @AfterAll
    public void close() throws IOException {
        // 关闭ES 客户端
        client.close();
    }

}
