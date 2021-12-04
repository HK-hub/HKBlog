package com.hkblog.search.test;



import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.util.Map;

/**
 * @author : HK意境
 * @ClassName : ESDocSearch
 * @date : 2021/12/2 11:22
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class ESDocSearch {

    private RestHighLevelClient client = null ;

    @BeforeAll
    public void init(){
        // 创建ES客户端
        client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );
    }


    // 查询数据
    public void getDocDataTest() throws IOException {

        GetRequest getRequest = new GetRequest();
        getRequest.index("user").id("1001");
        GetResponse documentFields = client.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(documentFields.getSourceAsString());


    }








    @AfterAll
    public void close() throws IOException {
        // 关闭ES 客户端
        client.close();
    }

}
