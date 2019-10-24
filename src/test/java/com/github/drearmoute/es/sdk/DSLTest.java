package com.github.drearmoute.es.sdk;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
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
    
    @Test
    public void termTest() throws Exception {
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.from(0);
        builder.size(1);
        builder.timeout(TimeValue.timeValueMinutes(1));
        builder.query(QueryBuilders.termQuery("name", "first"));
        SearchRequest request = new SearchRequest("user").source(builder);
        SearchResponse resp = client.search(request, RequestOptions.DEFAULT);
        System.err.println(resp);
    }
    
    /**
     * https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-query-builders.html
     * https://www.elastic.co/guide/en/elasticsearch/reference/6.0/query-filter-context.html
     * https://www.elastic.co/guide/en/elasticsearch/reference/6.0/search-request-stored-fields.html
     */

}
