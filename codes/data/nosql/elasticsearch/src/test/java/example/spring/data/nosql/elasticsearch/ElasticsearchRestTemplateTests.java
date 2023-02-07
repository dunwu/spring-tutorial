package example.spring.data.nosql.elasticsearch;

import example.spring.data.nosql.elasticsearch.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

/**
 * ElasticsearchRestTemplate 测试例
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-09-28
 */
@Slf4j
@SpringBootTest(classes = { DataElasticsearchApplication.class })
public class ElasticsearchRestTemplateTests {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @BeforeEach
    public void init() {
        elasticsearchRestTemplate.indexOps(Person.class).delete();
    }

    @Test
    @DisplayName("ElasticsearchRestTemplate 索引操作")
    public void indexOps() {
        log.info("索引 {} 是否存在：{}", "spring_tutorial_customer",
            elasticsearchRestTemplate.indexOps(Person.class).exists());
        log.info("创建索引 {} 是否成功：{}", "spring_tutorial_customer",
            elasticsearchRestTemplate.indexOps(Person.class).create());
    }

}
