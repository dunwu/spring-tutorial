package example.spring.data.nosql.mongodb;

import example.spring.data.nosql.mongodb.entity.Customer2;
import example.spring.data.nosql.mongodb.entity.CustomerDto;
import example.spring.data.nosql.mongodb.repository.CustomerProjection;
import example.spring.data.nosql.mongodb.repository.CustomerRepository2;
import example.spring.data.nosql.mongodb.repository.CustomerSummary;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.projection.TargetAware;

import java.util.Collection;

/**
 * Integration tests for {@link CustomerRepository2} to show projection capabilities.
 *
 * @author Oliver Gierke
 */
@SpringBootTest
public class CustomerRepository2Tests {

    @Autowired
    @Qualifier("customerRepository2")
    private CustomerRepository2 repository;

    Customer2 dave;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
        this.dave = repository.save(new Customer2("Dave", "Matthews"));
        repository.save(new Customer2("Carter", "Beauford"));
    }

    @Test
    public void projectsEntityIntoInterface() {
        Collection<CustomerProjection> result = repository.findAllProjectedBy();
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.iterator().next().getFirstName()).isEqualTo("Dave");
    }

    @Test
    public void projectsToDto() {

        Collection<CustomerDto> result = repository.findAllDtoedBy();

        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.iterator().next().getFirstName()).isEqualTo("Dave");
    }

    @Test
    public void projectsDynamically() {

        Collection<CustomerProjection> result = repository.findByFirstName("Dave", CustomerProjection.class);

        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.iterator().next().getFirstName()).isEqualTo("Dave");
    }

    @Test
    public void projectsIndividualDynamically() {

        CustomerSummary result = repository.findProjectedById(dave.getId(), CustomerSummary.class);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getFullName()).isEqualTo("Dave Matthews");

        // Proxy backed by original instance as the projection uses dynamic elements
        Assertions.assertThat(((TargetAware) result).getTarget()).isInstanceOf(Customer2.class);
    }

    @Test
    public void projectIndividualInstance() {

        CustomerProjection result = repository.findProjectedById(dave.getId(), CustomerProjection.class);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getFirstName()).isEqualTo("Dave");
        Assertions.assertThat(((TargetAware) result).getTarget()).isInstanceOf(Customer2.class);
    }

    @Test
    public void supportsProjectionInCombinationWithPagination() {

        Page<CustomerProjection> page = repository
            .findPagedProjectedBy(PageRequest.of(0, 1, Sort.by(Direction.ASC, "lastName")));

        Assertions.assertThat(page.getContent().get(0).getFirstName()).isEqualTo("Carter");
    }

}
