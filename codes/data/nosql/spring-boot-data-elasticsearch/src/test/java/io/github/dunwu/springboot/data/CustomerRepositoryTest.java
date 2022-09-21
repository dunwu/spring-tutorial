package io.github.dunwu.springboot.data;

import io.github.dunwu.springboot.DataElasticsearchApplication;
import io.github.dunwu.springboot.model.Customer;
import io.github.dunwu.springboot.repository.CustomerRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
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
    // @Autowired
    // private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void saveCustomers() {
        repository.save(new Customer("Alice", "北京", 13));
        repository.save(new Customer("Bob", "北京", 23));
        repository.save(new Customer("neo", "西安", 30));
        repository.save(new Customer("summer", "烟台", 22));
    }

    @Test
    public void fetchAllCustomers() {
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        Iterable<Customer> iterable = repository.findAll();
        for (Customer customer : iterable) {
            System.out.println(customer);
        }
    }

    @Test
    public void deleteCustomers() {
        repository.deleteAll();
        //        repository.deleteByUserName("neo");
    }

    @Test
    public void updateCustomers() {
        Customer customer = repository.findByName("summer");
        System.out.println(customer);
        customer.setAddress("北京市海淀区西直门");
        repository.save(customer);
        Customer xcustomer = repository.findByName("summer");
        System.out.println(xcustomer);
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
        System.out.println("Page customers " + customers.getContent().toString());
    }

    @Test
    public void fetchPage2Customers() {
        System.out.println("Customers found with fetchPageCustomers:");
        System.out.println("-------------------------------");
        QueryBuilder customerQuery = QueryBuilders.boolQuery()
                                                  .must(QueryBuilders.matchQuery("address", "北京"));
        Customer customer = new Customer();
        customer.setAddress("北京");

        Page<Customer> page = repository.searchSimilar(customer, new String[] { "address" }, PageRequest.of(0, 10));
        System.out.println("Page customers " + page.getContent().toString());
        // page = repository.search(customerQuery, PageRequest.of(1, 10));
        // System.out.println("Page customers " + page.getContent().toString());
    }
    //
    // @Test
    // public void fetchAggregation() {
    //     System.out.println("Customers found with fetchAggregation:");
    //     System.out.println("-------------------------------");
    //
    //     QueryBuilder customerQuery = QueryBuilders.boolQuery()
    //                                               .must(QueryBuilders.matchQuery("address", "北京"));
    //
    //     SumAggregationBuilder sumBuilder = AggregationBuilders.sum("sumAge").field("age");
    //
    //     NativeSearchQuery query = new NativeSearchQueryBuilder()
    //         .withQuery(customerQuery)
    //         .addAggregation(sumBuilder)
    //         .build();
    //
    //     SearchHits<Integer> hits = elasticsearchTemplate.search(query, Integer.class);
    //
    //     //转换成map集合
    //     // Map<String, Aggregation> aggregationMap = aggregations.asMap();
    //     // //获得对应的聚合函数的聚合子类，该聚合子类也是个map集合,里面的value就是桶Bucket，我们要获得Bucket
    //     // InternalSum sumAge = (InternalSum) aggregationMap.get("sumAge");
    //     // System.out.println("sum age is " + sumAge.getValue());
    // }

}
