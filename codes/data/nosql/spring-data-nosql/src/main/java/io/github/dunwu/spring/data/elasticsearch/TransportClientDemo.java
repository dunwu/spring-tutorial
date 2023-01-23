// package io.github.dunwu.spring.data.elasticsearch;
//
// import org.elasticsearch.action.search.SearchRequestBuilder;
// import org.elasticsearch.action.search.SearchResponse;
// import org.elasticsearch.client.transport.TransportClient;
// import org.elasticsearch.cluster.node.DiscoveryNode;
// import org.elasticsearch.common.settings.Settings;
// import org.elasticsearch.common.transport.TransportAddress;
// import org.elasticsearch.index.query.MatchAllQueryBuilder;
// import org.elasticsearch.index.query.QueryBuilders;
// import org.elasticsearch.search.SearchHit;
// import org.elasticsearch.search.SearchHits;
// import org.elasticsearch.transport.client.PreBuiltTransportClient;
//
// import java.net.InetAddress;
// import java.net.UnknownHostException;
// import java.util.List;
//
// /**
//  * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
//  * @date 2022-09-30
//  */
// public class TransportClientDemo {
//
//     public static void main(String[] args) throws UnknownHostException {
//         Settings settings = Settings.builder()
//                                     .put("client.transport.ping_timeout", "10s")
//                                     // 如果修改了 ES 默认集群名，此处必须设置
//                                     // .put("cluster.name", "elasticsearch")
//                                     .put("client.transport.sniff", true)
//                                     .build();
//
//         TransportClient client = new PreBuiltTransportClient(settings)
//             .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300))
//             .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.2"), 9300))
//             .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.3"), 9300));
//
//         List<DiscoveryNode> nodes = client.listedNodes();
//         System.out.println(nodes);
//
//         MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
//         SearchRequestBuilder searchRequestBuilder = client.prepareSearch("test")
//                                                           .setQuery(matchAllQueryBuilder)
//                                                           .setSize(5);
//         SearchResponse searchResponse = searchRequestBuilder.get();
//         SearchHits hits = searchResponse.getHits();
//         for (SearchHit hit : hits) {
//             System.out.println(hit.getSourceAsString());
//         }
//
//         client.close();
//     }
//
// }
