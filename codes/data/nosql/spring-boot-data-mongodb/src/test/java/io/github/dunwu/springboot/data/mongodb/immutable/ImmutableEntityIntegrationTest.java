package io.github.dunwu.springboot.data.mongodb.immutable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test for {@link ImmutablePerson} showing features around immutable object support.
 *
 * @author Mark Paluch
 * @author Christoph Strobl
 */
@SpringBootTest
public class ImmutableEntityIntegrationTest {

    @Autowired
    MongoOperations operations;

    @BeforeEach
    public void setUp() {
        operations.dropCollection(ImmutablePerson.class);
    }

    /**
     * Test case to show that automatically generated ids are assigned to the immutable domain object and how the {@link
     * ImmutablePerson#getRandomNumber()} gets set via {@link DataMongodbApplication#beforeConvertCallback()}.
     */
    @Test
    public void setsRandomNumberOnSave() {

        ImmutablePerson unsaved = new ImmutablePerson();
        assertThat(unsaved.getRandomNumber()).isZero();

        ImmutablePerson saved = operations.save(unsaved);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getRandomNumber()).isNotZero();
    }

}
