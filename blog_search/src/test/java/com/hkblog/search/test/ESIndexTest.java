package com.hkblog.search.test;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @author : HK意境
 * @ClassName : ESIndexTest
 * @date : 2021/12/1 20:38
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class ESIndexTest {

    private RestHighLevelClient client = null ;

    @Before
    public void init(){
        // 创建ES客户端
        client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );
    }

    // 创建索引
    @Test
    public void createIndexTest() throws IOException {

        // 创建index
        CreateIndexRequest request = new CreateIndexRequest("post");

        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        boolean state = response.isAcknowledged();
        System.out.println("创建索引：" + state);
        // 关闭ES 客户端
        client.close();

    }


    @Test
    public void searchIndexTest() throws IOException {

        GetIndexRequest getIndexRequest = new GetIndexRequest("post");

        GetIndexResponse getIndexResponse = client.indices().get(getIndexRequest, RequestOptions.DEFAULT);
        System.out.println("获取索引： " + getIndexResponse.toString());
        System.out.println(getIndexResponse.getMappings());
        System.out.println(getIndexResponse.getSettings());


    }


    @Test
    public void deleteIndexTest() throws IOException {

        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("post");
        AcknowledgedResponse delete = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged());

    }


    @After
    public void close() throws IOException {
        // 关闭ES 客户端
        client.close();
    }


}
