package io.github.dunwu.springboot.data.elasticsearch;

import cn.hutool.json.JSONUtil;
import io.github.dunwu.springboot.data.elasticsearch.model.Customer;
import io.github.dunwu.springboot.data.elasticsearch.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootTest(classes = { DataElasticsearchApplication.class })
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    public void aSaveCustomers() {
        repository.save(new Customer("Alice", "北京", 13));
        repository.save(new Customer("Bob", "北京", 23));
        repository.save(new Customer("neo", "西安", 30));
        repository.save(new Customer("summer", "烟台", 22));
    }

    @Test
    public void bFetchAllCustomers() {
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        Iterable<Customer> iterable = repository.findAll();
        for (Customer customer : iterable) {
            System.out.println(customer);
        }
    }

    @Test
    public void cUpdateCustomers() {
        Customer oldCustomer = repository.findByName("summer");
        System.out.println(oldCustomer);
        oldCustomer.setAddress("北京市海淀区西直门");
        repository.save(oldCustomer);
        Customer newCustomer = repository.findByName("summer");
        System.out.println(newCustomer);
    }

    @Test
    public void deleteCustomers() {
        repository.deleteAll();
    }

    @Test
    public void fetchIndividualCustomers() {
        System.out.println("Customer found with findByUserName('summer'):");
        System.out.println("--------------------------------");
        System.out.println(repository.findByName("summer"));
        System.out.println("--------------------------------");
        System.out.println("Customers found with findByAddress(\"北京\"):");
        String q = "北京";
        for (Customer customer : repository.findByAddress(q)) {
            System.out.println(customer);
        }
    }

    @Test
    public void fetchPageCustomers() {
        System.out.println("Customers found with fetchPageCustomers:");
        System.out.println("-------------------------------");
        Sort sort = Sort.by(Sort.Direction.DESC, "address.keyword");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<Customer> customers = repository.findByAddress("北京", pageable);
        System.out.println("Page customers: " + JSONUtil.toJsonPrettyStr(customers));
    }

}
