package example.spring.data.nosql.mongodb;

import example.spring.data.nosql.mongodb.entity.Customer;
import example.spring.data.nosql.mongodb.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoOperations;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class DataMongodbApplication implements CommandLineRunner {

    public static final String SYSTEM_PROFILE_DB = "system.profile";

    private final MongoOperations operations;

    private final CustomerRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(DataMongodbApplication.class, args);
    }

    @Override
    public void run(String... args) {

        repository.deleteAll();

        // save a couple of customers
        repository.save(new Customer("Alice", "Smith"));
        repository.save(new Customer("Bob", "Smith"));

        // fetch all customers
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (Customer customer : repository.findAll()) {
            System.out.println(customer);
        }
        System.out.println();

        // fetch an individual customer
        System.out.println("Customer found with findByFirstName('Alice'):");
        System.out.println("--------------------------------");
        System.out.println(repository.findByFirstName("Alice"));

        System.out.println("Customers found with findByLastName('Smith'):");
        System.out.println("--------------------------------");
        for (Customer customer : repository.findByLastName("Smith")) {
            System.out.println(customer);
        }
    }

    /**
     * Initialize db instance with defaults.
     */
    @PostConstruct
    public void initializeWithDefaults() {

        // Enable profiling
        setProfilingLevel(2);
    }

    /**
     * Clean up resources on shutdown
     */
    @PreDestroy
    public void cleanUpWhenShuttingDown() {

        // Disable profiling
        setProfilingLevel(0);

        if (operations.collectionExists(SYSTEM_PROFILE_DB)) {
            operations.dropCollection(SYSTEM_PROFILE_DB);
        }
    }

    private void setProfilingLevel(int level) {
        operations.executeCommand(new Document("profile", level));
    }

}
