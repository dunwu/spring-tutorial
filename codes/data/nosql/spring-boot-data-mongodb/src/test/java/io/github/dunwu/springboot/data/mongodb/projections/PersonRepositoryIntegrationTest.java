package io.github.dunwu.springboot.data.mongodb.projections;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.projection.TargetAware;

import java.util.Collection;

/**
 * Integration tests for {@link CustomerRepository} to show projection capabilities.
 *
 * @author Oliver Gierke
 */
@SpringBootTest
public class PersonRepositoryIntegrationTest {

    @Configuration
    @EnableAutoConfiguration
    static class Config { }

    @Autowired
    @Qualifier("projectionsCustomerRepository")
    CustomerRepository customers;

    Customer dave, carter;

    @BeforeEach
    public void setUp() {

        customers.deleteAll();

        this.dave = customers.save(new Customer("Dave", "Matthews"));
        this.carter = customers.save(new Customer("Carter", "Beauford"));
    }

    @Test
    public void projectsEntityIntoInterface() {

        Collection<CustomerProjection> result = customers.findAllProjectedBy();

        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.iterator().next().getFirstname()).isEqualTo("Dave");
    }

    @Test
    public void projectsToDto() {

        Collection<CustomerDto> result = customers.findAllDtoedBy();

        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.iterator().next().getFirstname()).isEqualTo("Dave");
    }

    @Test
    public void projectsDynamically() {

        Collection<CustomerProjection> result = customers.findByFirstname("Dave", CustomerProjection.class);

        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.iterator().next().getFirstname()).isEqualTo("Dave");
    }

    @Test
    public void projectsIndividualDynamically() {

        CustomerSummary result = customers.findProjectedById(dave.getId(), CustomerSummary.class);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getFullName()).isEqualTo("Dave Matthews");

        // Proxy backed by original instance as the projection uses dynamic elements
        Assertions.assertThat(((TargetAware) result).getTarget()).isInstanceOf(Customer.class);
    }

    @Test
    public void projectIndividualInstance() {

        CustomerProjection result = customers.findProjectedById(dave.getId());

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getFirstname()).isEqualTo("Dave");
        Assertions.assertThat(((TargetAware) result).getTarget()).isInstanceOf(Customer.class);
    }

    @Test
    public void supportsProjectionInCombinationWithPagination() {

        Page<CustomerProjection> page = customers
            .findPagedProjectedBy(PageRequest.of(0, 1, Sort.by(Direction.ASC, "lastname")));

        Assertions.assertThat(page.getContent().get(0).getFirstname()).isEqualTo("Carter");
    }

}
