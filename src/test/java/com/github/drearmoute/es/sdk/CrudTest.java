package com.github.drearmoute.es.sdk;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.TermVectorsRequest;
import org.elasticsearch.client.core.TermVectorsResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.alibaba.fastjson.JSON;

public class CrudTest {

    private static final String INDEX = "user";
    private static String userJson;
    private static User user = new User();
    private static RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("10.82.12.67", 9200, "http")));

    @BeforeAll
    public static void initDataTest() {
        user.setId(102L);
        user.setName("this is the w.dehai's first data.");
        user.setPassword("123456789");
        user.setAge(30);
        userJson = JSON.toJSONString(user);
    }
    
    @BeforeAll
    public static void closeClient() throws Exception {
//        client.close();
    }

    @Test
    public void indexTest() throws Exception {
        IndexRequest request = new IndexRequest(INDEX)
                .id(String.valueOf(user.getId()))
                .source(userJson, XContentType.JSON);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        System.err.println(response);
    }
    
    @Test
    public void getTest() throws Exception {
        GetRequest request = new GetRequest(INDEX, String.valueOf(user.getId()));
        GetResponse resp = client.get(request, RequestOptions.DEFAULT);
        String userJson = resp.getSourceAsString();
        System.err.println(userJson);
    }
    
    @Test
    public void updateTest() throws Exception {
        UpdateRequest request = new UpdateRequest(INDEX, String.valueOf(user.getId()))
                .doc(userJson, XContentType.JSON);
        UpdateResponse resp = client.update(request, RequestOptions.DEFAULT);
        System.err.println(resp);
    }
    
    @Test
    public void termVectorsTest() throws Exception {
        TermVectorsRequest request = new TermVectorsRequest(INDEX, String.valueOf(user.getId()));
        request.setFields("name");
        TermVectorsResponse resp = client.termvectors(request, RequestOptions.DEFAULT);
        System.err.println(resp);
    }
    
    @Test
    public void deleteIndexTest() throws Exception {
        
    }

}
