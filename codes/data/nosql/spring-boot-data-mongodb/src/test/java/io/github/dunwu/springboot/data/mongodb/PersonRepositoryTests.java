package io.github.dunwu.springboot.data.mongodb;

import io.github.dunwu.springboot.data.mongodb.person.Person;
import io.github.dunwu.springboot.data.mongodb.person.PersonRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = { DataMongodbApplication.class })
@ExtendWith(SpringExtension.class)
@DisplayName("MongoDB 测试")
public class PersonRepositoryTests {

    @Autowired
    private PersonRepository repository;

    private Person dave, oliver, carter;

    @BeforeEach
    public void setUp() {

        repository.deleteAll();

        dave = repository.save(new Person("Dave", "Matthews"));
        oliver = repository.save(new Person("Oliver August", "Matthews"));
        carter = repository.save(new Person("Carter", "Beauford"));
    }

    @Nested
    @DisplayName("MongoDB 基本 CRUD 测试")
    class CRUDTests {

        @Test
        public void findsByExample() {

            Person probe = new Person(null, "Matthews");

            List<Person> result = repository.findAll(Example.of(probe));

            Assertions.assertThat(result).hasSize(2).extracting("firstName").contains("Dave", "Oliver August");
        }

        @Test
        public void findsByLastName() {

            List<Person> result = repository.findByLastName("Beauford");

            Assertions.assertThat(result).hasSize(1).extracting("firstName").contains("Carter");
        }

        @Test
        public void setsIdOnSave() {

            Person dave = repository.save(new Person("Dave", "Matthews"));

            assertThat(dave.id).isNotNull();
        }

    }

}
