package com.github.drearmoute.es.sdk;

import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.AnalyzeRequest;
import org.elasticsearch.client.indices.AnalyzeResponse;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.DetailAnalyzeResponse;
import org.elasticsearch.client.indices.GetFieldMappingsRequest;
import org.elasticsearch.client.indices.GetFieldMappingsResponse;
import org.elasticsearch.client.indices.GetFieldMappingsResponse.FieldMappingMetaData;
import org.elasticsearch.client.indices.GetMappingsRequest;
import org.elasticsearch.client.indices.GetMappingsResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;

import com.alibaba.fastjson.JSON;
import com.github.drearmoute.es.sdk.mapping.Mapping;
import com.github.drearmoute.es.sdk.mapping.Type;

/**
 * 分词文档：https://www.jianshu.com/p/40e33c84693d
 * 
 * @author w.dehai
 *
 */
public class IndexApiTest {

    private static RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("10.82.12.67", 9200, "http")));

    @Test
    public void analyzeTest() throws Exception {
        AnalyzeRequest req = AnalyzeRequest.withIndexAnalyzer("w.dehai", "english", "China is a beautiful country.");
        AnalyzeResponse resp = client.indices().analyze(req, RequestOptions.DEFAULT);
        req.explain(true);
        System.err.println(resp);
        DetailAnalyzeResponse dar = resp.detail();
        System.err.println(dar);
    }

    @Test
    public void createIndexTest() throws Exception {
        CreateIndexRequest request = new CreateIndexRequest("mim");
        request.settings(Settings.builder().put("index.number_of_shards", 2).put("index.number_of_replicas", 2).build());
        CreateIndexResponse resp = client.indices().create(request, RequestOptions.DEFAULT);
        System.err.println(JSON.toJSONString(resp, true));
    }

    @Test
    public void mappingTest() throws Exception {
        Mapping mapping = new Mapping();
        mapping.put("name", Type.builder().type("text").build());
        CreateIndexRequest request = new CreateIndexRequest("mimi");
        request.mapping(mapping.toJSON(), XContentType.JSON);
        CreateIndexResponse resp = client.indices().create(request, RequestOptions.DEFAULT);
        System.err.println(resp);
    }
    
    @Test
    public void getMappingTest() throws Exception {
        GetMappingsRequest request = new GetMappingsRequest();
        request.indices("mimi");
        GetMappingsResponse resp = client.indices().getMapping(request, RequestOptions.DEFAULT);
        resp.mappings();
    }
    
    @Test
    public void getFieldMappingTest() throws Exception {
        GetFieldMappingsRequest request = new GetFieldMappingsRequest();
        request.indices("mimi");
        request.fields("name");
        GetFieldMappingsResponse resp = client.indices().getFieldMapping(request, RequestOptions.DEFAULT);
        Map<String, Map<String, FieldMappingMetaData>> maps = resp.mappings();
        System.err.println(maps);
    }
    
    @Test
    public void deleteIndexTest() throws Exception {
        
    }

}




















