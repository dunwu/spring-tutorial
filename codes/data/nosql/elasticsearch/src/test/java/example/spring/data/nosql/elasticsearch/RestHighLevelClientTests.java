package example.spring.data.nosql.elasticsearch;

import cn.hutool.core.util.StrUtil;
import example.spring.data.nosql.elasticsearch.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-09-28
 */
@SpringBootTest(classes = { DataElasticsearchApplication.class })
public class RestHighLevelClientTests {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @BeforeEach
    public void init() {
        elasticsearchRestTemplate.indexOps(Customer.class).delete();
    }

    @Test
    @DisplayName("ElasticsearchRestTemplate 索引操作")
    public void indexOps() {
        System.out.println(StrUtil.format("索引是否存在：{}", elasticsearchRestTemplate.indexOps(Customer.class).exists()));
        System.out.println(StrUtil.format("创建索引：{}", elasticsearchRestTemplate.indexOps(Customer.class).create()));
    }

}
