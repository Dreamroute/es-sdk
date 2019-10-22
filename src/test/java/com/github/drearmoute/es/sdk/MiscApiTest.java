package com.github.drearmoute.es.sdk;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.MainResponse;
import org.junit.jupiter.api.Test;

public class MiscApiTest {
    
    private static RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("10.82.12.67", 9200, "http")));

    @Test
    public void clusterInfoTest() throws Exception {
        MainResponse response = client.info(RequestOptions.DEFAULT);
        
        String clusterName = response.getClusterName();
        String clusterUuid = response.getClusterUuid();
        String nodeName = response.getNodeName();
        MainResponse.Version version = response.getVersion();
        String buildDate = version.getBuildDate();
        String buildFlavor = version.getBuildFlavor();
        String buildHash = version.getBuildHash();
        String buildType = version.getBuildType();
        String luceneVersion = version.getLuceneVersion();
        String minimumIndexCompatibilityVersion= version.getMinimumIndexCompatibilityVersion();
        String minimumWireCompatibilityVersion = version.getMinimumWireCompatibilityVersion();
        String number = version.getNumber();
        
        System.err.println(clusterName);
        System.err.println(clusterUuid);
        System.err.println(nodeName);
        System.err.println(buildDate);
        System.err.println(buildFlavor);
        System.err.println(buildHash);
        System.err.println(buildType);
        System.err.println(luceneVersion);
        System.err.println(minimumIndexCompatibilityVersion);
        System.err.println(minimumWireCompatibilityVersion);
        System.err.println(number);
    }
    
    @Test
    public void pingTest() throws Exception {
        boolean response = client.ping(RequestOptions.DEFAULT);
        assertEquals(true, response);
    }

}
