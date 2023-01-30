package example.spring.data.nosql.mongodb;

import example.spring.data.nosql.mongodb.entity.Address;
import example.spring.data.nosql.mongodb.entity.Customer3;
import example.spring.data.nosql.mongodb.repository.CustomerRepository3;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.GeospatialIndex;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

/**
 * Integration test for {@link CustomerRepository3}.
 *
 * @author Oliver Gierke
 */
@SpringBootTest
public class CustomerRepository3Tests {

    @Autowired
    @Qualifier("customerRepository3")
    private CustomerRepository3 repository;

    @Autowired
    private MongoOperations operations;

    Customer3 dave, oliver, carter;

    @BeforeEach
    public void setUp() {

        repository.deleteAll();

        dave = repository.save(new Customer3("Dave", "Matthews"));
        oliver = repository.save(new Customer3("Oliver August", "Matthews"));
        carter = repository.save(new Customer3("Carter", "Beauford"));
    }

    @Test
    @DisplayName("测试保存记录")
    public void save() {
        Customer3 dave = repository.save(new Customer3("Dave", "Matthews"));
        Assertions.assertThat(dave.getId()).isNotNull();
    }

    @Test
    @DisplayName("测试 GEO API")
    public void exposesGeoSpatialFunctionality() {

        GeospatialIndex indexDefinition = new GeospatialIndex("address.location");
        indexDefinition.getIndexOptions().put("min", -180);
        indexDefinition.getIndexOptions().put("max", 180);

        operations.indexOps(Customer3.class).ensureIndex(indexDefinition);

        Customer3 ollie = new Customer3("Oliver", "Gierke");
        ollie.setAddress(new Address(new Point(52.52548, 13.41477)));
        ollie = repository.save(ollie);

        Point referenceLocation = new Point(52.51790, 13.41239);
        Distance oneKilometer = new Distance(1, Metrics.KILOMETERS);

        GeoResults<Customer3> result = repository.findByAddressLocationNear(referenceLocation, oneKilometer);

        assertThat(result.getContent(), hasSize(1));

        Distance distanceToFirstStore = result.getContent().get(0).getDistance();
        assertThat(distanceToFirstStore.getMetric(), is(Metrics.KILOMETERS));
        assertThat(distanceToFirstStore.getValue(), closeTo(0.862, 0.001));
    }

}
