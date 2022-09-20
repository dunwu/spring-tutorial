package io.github.dunwu.springboot.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CustomerRepository extends MongoRepository<Person, String> {

    Person findByFirstName(String firstName);

    List<Person> findByLastName(String lastName);

}
