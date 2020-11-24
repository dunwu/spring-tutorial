package io.github.dunwu.springboot;

import io.github.dunwu.springboot.mongodb.CustomerRepository;
import io.github.dunwu.springboot.mongodb.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SpringBootDataMongodbApplication implements CommandLineRunner {

    @Autowired
    private CustomerRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDataMongodbApplication.class, args);
    }

    @Override
    public void run(String... args) {

        repository.deleteAll();

        // save a couple of customers
        repository.save(new Person("Alice", "Smith"));
        repository.save(new Person("Bob", "Smith"));

        // fetch all customers
        log.info("Customers found with findAll():");
        log.info("-------------------------------");
        for (Person person : repository.findAll()) {
            log.info(person.toString());
        }

        // fetch an individual customer
        log.info("Customer found with findByFirstName('Alice'):");
        log.info("--------------------------------");
        log.info(repository.findByFirstName("Alice").toString());

        log.info("Customers found with findByLastName('Smith'):");
        log.info("--------------------------------");
        for (Person person : repository.findByLastName("Smith")) {
            log.info(person.toString());
        }
    }

}
