
package example.spring.data.nosql.elasticsearch.repository;

import example.spring.data.nosql.elasticsearch.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface CustomerRepository extends ElasticsearchRepository<Customer, String> {

    List<Customer> findByAddress(String address);

    Customer findByName(String name);

    int deleteByName(String name);

    Page<Customer> findByAddress(String address, Pageable pageable);

}
