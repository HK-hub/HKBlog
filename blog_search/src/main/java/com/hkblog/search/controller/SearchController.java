package com.hkblog.search.controller;

import com.alibaba.fastjson.JSON;
import com.hkblog.business.service.impl.PostServiceImpl;
import com.hkblog.common.response.ResponseResult;
import com.hkblog.common.response.ResultCode;
import com.hkblog.domain.entity.Post;
import com.hkblog.search.dao.PostDao;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author : HK意境
 * @ClassName : SearchController
 * @date : 2021/12/2 22:06
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Slf4j
@EnableScheduling
@RefreshScope
@CrossOrigin
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate ;
    @Autowired
    private PostDao postDao ;
    @Autowired
    private PostServiceImpl postService ;


    /**
     * @methodName : 查询文章，通过关键字
     * @author : HK意境
     * @date : 2021/12/2 22:07
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
    @GetMapping("/post")
    public ResponseResult searchPost(@RequestParam(name = "keyword")String keyword, @PageableDefault(size = 10) Pageable pageable){

        try{
            // 查询构造
            SearchQuery searchQuery = new NativeSearchQueryBuilder()
                    // 标题查询
                    //.withQuery(QueryBuilders.matchQuery("title", keyword))
                    // 查询描述，摘要
                    //.withQuery(QueryBuilders.matchQuery("description", keyword))
                    // 查询文章内容
                    //.withQuery(QueryBuilders.matchQuery("content", keyword))
                    .withQuery(QueryBuilders.queryStringQuery(keyword))
                    .withQuery(QueryBuilders.boolQuery()
                            .should(QueryBuilders.matchQuery("title", keyword))
                            .should(QueryBuilders.matchQuery("description", keyword))
                            .should(QueryBuilders.matchQuery("content", keyword)))
                    // 分页查询
                    .withPageable(pageable)
                    // 高亮
                    .withHighlightFields(
                            // 标题高亮
                            new HighlightBuilder.Field("title").preTags("<em style='color:red'>").postTags("</em>"),
                            // 描述高亮
                            new HighlightBuilder.Field("description").preTags("<em style='color:red'>").postTags("</em>"))
                            // 内容高亮
                            //new HighlightBuilder.Field("content").preTags("<em style='color:red'>").postTags("</em>"))
                    // 排序
                    .withSort(SortBuilders.scoreSort())
                    .withSort(SortBuilders.fieldSort("createTime"))
                    .build();

            // 高亮数据字段
            String[] hlf = {"title","description","content"};

            // 查询数据
            AggregatedPage<Post> queryForPage = elasticsearchRestTemplate.queryForPage(searchQuery, Post.class, new SearchResultMapper() {
                @Override
                public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {

                    List<T> chunk = new ArrayList<T>();

                    // 获取命中数据
                    for (SearchHit searchHit : searchResponse.getHits()) {
                        if (searchResponse.getHits().getHits().length <= 0) {
                            return null;
                        }

                        // 获取命中数据的Map 集合
                        Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
                        // 获取高亮字段数据
                        Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
                        // 高亮数据字段处理
                        for (String highName : hlf) {
                            // 获取高亮数据
                            if (highlightFields.containsKey(highName)) {
                                // 包含高亮字段
                                String highValue = highlightFields.get(highName).fragments()[0].toString();
                                // 向源中重写高亮数据
                                sourceAsMap.put(highName, highValue);
                            }
                        }
                        // 构造对象
                        try {

                            T post = JSON.parseObject(JSON.toJSONString(sourceAsMap), aClass);
                            chunk.add(post);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    // 封装返回结果
                    AggregatedPageImpl<T> aggregatedPage = new AggregatedPageImpl<T>(chunk, pageable, searchResponse.getHits().getTotalHits());

                    return aggregatedPage;
                }

                @Override
                public <T> T mapSearchHit(SearchHit searchHit, Class<T> aClass) {
                    return null;
                }
            });
            return new ResponseResult(ResultCode.SUCCESS, queryForPage) ;

        }catch (Exception e){
            e.printStackTrace();
        }

        return null ;

    }


    // 使用定时任务，定时执行 : 每隔1分钟执行一次：0 */1 * * * ?
    @Scheduled(cron = "0 */20 * * * ?")
    @PutMapping("/save")
    public ResponseResult savePost(){

        log.info("[scheduling task: start put or update data into elasticsearch : {}]",new Date());
        List<Post> postList = postService.list();
        postDao.saveAll(postList);
        log.info("[scheduling task: end put or update data into elasticsearch : {}]",new Date());
        return new ResponseResult(ResultCode.SUCCESS) ;
    }


    /**
     * @methodName : 删除索引数据库
     * @author : HK意境
     * @date : 2021/12/3 18:07
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
    @DeleteMapping
    public ResponseResult deleteIndex(@RequestParam(name = "index")String index){

        //DeleteIndexRequest indices = new DeleteIndexRequest().indices(index);
        boolean b = elasticsearchRestTemplate.deleteIndex(index);
        if (b){
            return new ResponseResult(ResultCode.SUCCESS,"删除索引:" + index + "成功");
        }
        return new ResponseResult(ResultCode.FAIL,"删除索引:" + index + "失败!");
    }


}
