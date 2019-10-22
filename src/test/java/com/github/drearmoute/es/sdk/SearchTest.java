package com.github.drearmoute.es.sdk;

import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
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
    
    @Test
    public void fulltextTest() throws Exception {
        MatchQueryBuilder query = new MatchQueryBuilder("name", "w.dehai");
        query.fuzziness(Fuzziness.AUTO);
        query.prefixLength(3);
        query.maxExpansions(10);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(query);
        SearchRequest request = new SearchRequest(INDEX);
        request.source(builder);
        SearchResponse resp = client.search(request, RequestOptions.DEFAULT);
        System.err.println(resp);
    }
    
    /**
     * 高亮需要注意：如果把SearchResponse直接调用JSON.toJSONString打印出来，是无法完全把SearchResponse显示正确的，而是需要调用resp.toString()才能显示正确的字符串结果
     */
    @Test
    public void highlightTest() throws Exception {
        SearchSourceBuilder builder = new SearchSourceBuilder();
        MatchQueryBuilder query = new MatchQueryBuilder("name", "w.dehai");
        query.fuzziness(Fuzziness.AUTO);
        query.prefixLength(3);
        query.maxExpansions(10);
        builder.query(query);
        
        HighlightBuilder hbuilder = new HighlightBuilder();
        HighlightBuilder.Field nameField = new HighlightBuilder.Field("name");
        nameField.highlighterType("unified");
        hbuilder.field(nameField);
        builder.highlighter(hbuilder);
        
        SearchRequest request = new SearchRequest(INDEX);
        request.source(builder);
        SearchResponse resp = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = resp.getHits();
        long totalHits = hits.getTotalHits().value;
        if (totalHits > 0) {
            hits.forEach(hit -> {
                String source = hit.getSourceAsString();
                System.err.println(source);
                Map<String, HighlightField> map = hit.getHighlightFields();
                HighlightField hf = map.get("name");
                Text[] fragments = hf.getFragments();
                if (fragments != null && fragments.length > 0) {
                    for (Text text : fragments) {
                        System.err.println(text.string());
                    }
                }
            });
        }
    }
    
    @Test
    public void scrollSearchTest() throws Exception {
        SearchRequest request = new SearchRequest();
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());
        builder.size(2);
        request.source(builder);
        request.scroll(TimeValue.timeValueMinutes(1L));
        
        SearchResponse resp = client.search(request, RequestOptions.DEFAULT);
        String scrollId = resp.getScrollId();
        SearchHits hits = resp.getHits();
        System.err.println(scrollId);
        long total = hits.getTotalHits().value;
        if ( total> 0L) {
            hits.forEach(hit -> {
                System.err.println(hit.getSourceAsString());
            });
        }
    }

}

















