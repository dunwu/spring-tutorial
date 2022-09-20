package io.github.dunwu.springboot;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.indices.CreateIndex;

import java.io.IOException;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-11-21
 */
public class JestClientDemo {

    public static void main(String[] args) throws IOException {
        // Construct a new Jest client according to configuration via factory
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
            .Builder("http://172.22.6.9:9200")
            .multiThreaded(true)
            //Per default this implementation will create no more than 2 concurrent connections per given route
            .defaultMaxTotalConnectionPerRoute(2)
            // and no more 20 connections in total
            .maxTotalConnection(100)
            .build());
        JestClient client = factory.getObject();
        JestResult result = client.execute(new CreateIndex.Builder("user").build());
        System.out.println("result = " + result.getJsonString());
    }

}
