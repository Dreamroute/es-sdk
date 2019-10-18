package com.github.drearmoute.es.sdk;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;

import com.alibaba.fastjson.JSON;

public class SearchTest {
    
    private static RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("10.82.12.67", 9200, "http")));
    private static final String INDEX = "user";
    
    @Test
    public void matchAllTest() throws Exception {
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());
        SearchRequest request = new SearchRequest(INDEX);
        request.source(builder);
        SearchResponse resp = client.search(request, RequestOptions.DEFAULT);
        System.err.println(JSON.toJSONString(resp, true));
    }
    
    @Test
    public void termQueryTest() throws Exception {
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.termQuery("name", "w.dehai"));
        SearchRequest request = new SearchRequest(INDEX);
        request.source(builder);
        SearchResponse resp = client.search(request, RequestOptions.DEFAULT);
        System.err.println(JSON.toJSONString(resp, true));
    }

}
