package example.spring.data.nosql.elasticsearch;

import cn.hutool.json.JSONUtil;
import example.spring.data.nosql.elasticsearch.model.Person;
import example.spring.data.nosql.elasticsearch.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Slf4j
@SpringBootTest(classes = { DataElasticsearchApplication.class })
public class PersonRepositoryTests {

    @Autowired
    private PersonRepository repository;

    @BeforeEach
    public void saveCustomers() {
        repository.deleteAll();
        repository.save(new Person("张三", "南京", 18));
        repository.save(new Person("李四", "北京", 23));
        repository.save(new Person("王五", "上海", 20));
        repository.save(new Person("赵六", "深圳", 22));
    }

    @Test
    public void deleteCustomers() {
        Assertions.assertThat(repository.count()).isGreaterThan(0);
        repository.deleteAll();
        Assertions.assertThat(repository.count()).isEqualTo(0);
    }

    @Test
    public void updateCustomers() {
        Person customer = repository.findByName("李四");
        Assertions.assertThat(customer).isNotNull();
        Assertions.assertThat(customer.getAddress()).isEqualTo("北京");

        customer.setAddress("北京市海淀区西直门");
        repository.save(customer);

        Person customer2 = repository.findByName("李四");
        Assertions.assertThat(customer2).isNotNull();
        Assertions.assertThat(customer2.getAddress()).isEqualTo("北京市海淀区西直门");
    }

    @Test
    public void findAll() {
        Iterable<Person> iterable = repository.findAll();
        Assertions.assertThat(iterable).isNotEmpty();
        for (Person customer : iterable) {
            log.info(JSONUtil.toJsonStr(customer));
        }
    }

    @Test
    public void findByXXX() {
        Person customer = repository.findByName("李四");
        Assertions.assertThat(customer).isNotNull();

        List<Person> list = repository.findByAddress("北京");
        Assertions.assertThat(list).isNotEmpty();
    }

    @Test
    public void findByXXXPage() {
        Sort sort = Sort.by(Sort.Direction.DESC, "address.keyword");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<Person> page = repository.findByAddress("北京", pageable);
        Assertions.assertThat(page).isNotNull();
        Assertions.assertThat(page.getContent()).isNotNull();
        Assertions.assertThat(page.getContent().get(0).getName()).isEqualTo("李四");
    }

}
