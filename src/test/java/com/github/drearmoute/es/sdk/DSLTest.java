package com.github.drearmoute.es.sdk;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;

public class DSLTest {
    
    private static RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("10.82.12.67", 9200, "http")));
    
    @Test
    public void boolTest() throws Exception {
        SearchSourceBuilder builder = new SearchSourceBuilder();
        QueryBuilder query = QueryBuilders
                .boolQuery()
                    .must(QueryBuilders.matchQuery("name", "w.dehai"))
                    .filter(QueryBuilders.termQuery("id", 100L));
        builder.query(query);
        SearchRequest request = new SearchRequest("w.dehai");
        request.source(builder);
        SearchResponse resp = client.search(request, RequestOptions.DEFAULT);
        System.err.println(resp);
    }

}
