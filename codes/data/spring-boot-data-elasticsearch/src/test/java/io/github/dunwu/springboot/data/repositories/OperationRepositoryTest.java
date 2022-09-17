package io.github.dunwu.springboot.data.repositories;

import cn.hutool.core.collection.CollUtil;
import io.github.dunwu.springboot.data.entities.Operation;
import io.github.dunwu.springboot.data.entities.Sector;
import io.github.dunwu.springboot.SpringBootDataElasticsearchApplication;
import io.github.dunwu.tool.util.RandomUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SpringBootDataElasticsearchApplication.class })
public class OperationRepositoryTest {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private OperationRepository repository;

    @Before
    public void clear() {
        repository.deleteAll();
    }

    @Test
    public void getMappingTest() {
        Map mapping = elasticsearchTemplate.getMapping(Operation.class);
        System.out.println(mapping.toString());
    }

    @Test
    public void test() {
        Sector sector1 = new Sector(1, RandomUtil.randomString(5, 10));
        Sector sector2 = new Sector(2, RandomUtil.randomString(5, 10));
        Sector sector3 = new Sector(3, RandomUtil.randomString(5, 10));
        ArrayList<Sector> list = CollUtil.newArrayList(sector1, sector2, sector3);
        Operation operation = new Operation(1L, RandomUtil.randomString(5, 10), "2010-01-01 12:00:00",
            RandomUtil.randomString(5, 10), list);
        repository.save(operation);

        Iterable<Operation> iterable = repository.findAll();
        iterable.forEach(System.out::println);
    }

}
