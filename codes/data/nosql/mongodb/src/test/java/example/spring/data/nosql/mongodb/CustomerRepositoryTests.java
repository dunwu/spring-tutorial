package example.spring.data.nosql.mongodb;

import example.spring.data.nosql.mongodb.entity.Customer;
import example.spring.data.nosql.mongodb.repository.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@SpringBootTest(classes = { DataMongodbApplication.class })
@ExtendWith(SpringExtension.class)
@DisplayName("MongoDB 基本 CRUD 测试")
public class CustomerRepositoryTests {

    @Autowired
    private CustomerRepository repository;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();

        repository.save(new Customer("Dave", "Matthews"));
        repository.save(new Customer("Oliver August", "Matthews"));
        repository.save(new Customer("Carter", "Beauford"));
    }

    @Test
    @DisplayName("测试根据 Example 查询")
    public void findAll() {
        Customer probe = new Customer(null, "Matthews");
        List<Customer> result = repository.findAll(Example.of(probe));
        Assertions.assertThat(result).hasSize(2).extracting("firstName").contains("Dave", "Oliver August");
    }

    @Test
    @DisplayName("测试根据 findByXXX 方法查询")
    public void findByLastName() {
        List<Customer> result = repository.findByLastName("Beauford");
        Assertions.assertThat(result).hasSize(1).extracting("firstName").contains("Carter");
    }

    @Test
    @DisplayName("测试保存记录")
    public void save() {
        Customer dave = repository.save(new Customer("Dave", "Matthews"));
        Assertions.assertThat(dave.getId()).isNotNull();
    }

}
