package com.hkblog.search.test;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;

import java.io.IOException;

/**
 * @author : HK意境
 * @ClassName : ESClientTest
 * @date : 2021/12/1 20:34
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class ESClientTest {

    @Test
    public void clientTest() throws IOException {
        // 创建ES客户端
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );



        // 关闭ES 客户端
        restHighLevelClient.close();
    }





}
