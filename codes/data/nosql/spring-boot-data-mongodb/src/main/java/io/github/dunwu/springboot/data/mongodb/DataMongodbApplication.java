package io.github.dunwu.springboot.data.mongodb;

import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoOperations;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@SpringBootApplication
public class DataMongodbApplication implements CommandLineRunner {

    public static final String SYSTEM_PROFILE_DB = "system.profile";

    @Autowired
    private MongoOperations operations;
    // @Autowired
    // private PersonRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(DataMongodbApplication.class, args);
    }

    @Override
    public void run(String... args) {

        // repository.deleteAll();
        //
        // // save a couple of customers
        // repository.save(new Person("Alice", "Smith"));
        // repository.save(new Person("Bob", "Smith"));
        //
        // // fetch all customers
        // log.info("Customers found with findAll():");
        // log.info("-------------------------------");
        // for (Person person : repository.findAll()) {
        //     log.info(person.toString());
        // }
        //
        // // fetch an individual customer
        // log.info("Customer found with findByFirstName('Alice'):");
        // log.info("--------------------------------");
        // log.info(repository.findByFirstName("Alice").toString());
        //
        // log.info("Customers found with findByLastName('Smith'):");
        // log.info("--------------------------------");
        // for (Person person : repository.findByLastName("Smith")) {
        //     log.info(person.toString());
        // }
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

    // /**
    //  * Register the {@link BeforeConvertCallback} used to update an {@link ImmutablePerson} before handing over the
    //  * newly created instance to the actual mapping layer performing the conversion into the store native {@link
    //  * org.bson.Document} representation.
    //  *
    //  * @return a {@link BeforeConvertCallback} for {@link ImmutablePerson}.
    //  */
    // @Bean
    // BeforeConvertCallback<ImmutablePerson> beforeConvertCallback() {
    //
    //     return (immutablePerson, collection) -> {
    //
    //         int randomNumber = ThreadLocalRandom.current().nextInt(1, 100);
    //
    //         // withRandomNumber is a so called wither method returning a new instance of the entity with a new value assigned
    //         return immutablePerson.withRandomNumber(randomNumber);
    //     };
    // }

}
