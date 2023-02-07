
package example.spring.data.nosql.elasticsearch.repository;

import example.spring.data.nosql.elasticsearch.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface PersonRepository extends ElasticsearchRepository<Person, String> {

    List<Person> findByAddress(String address);

    Page<Person> findByAddress(String address, Pageable pageable);

    Person findByName(String name);

    int deleteByName(String name);

}
