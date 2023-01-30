package example.spring.data.nosql.mongodb.repository;

import example.spring.data.nosql.mongodb.entity.Customer3;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface to manage {@link Customer3} instances.
 *
 * @author Oliver Gierke
 */
@Repository("customerRepository3")
public interface CustomerRepository3 extends CrudRepository<Customer3, String> {

    /**
     * Derived query using dynamic sort information.
     */
    List<Customer3> findByLastName(String lastName, Sort sort);

    /**
     * Show case for a repository query using geo-spatial functionality.
     */
    GeoResults<Customer3> findByAddressLocationNear(Point point, Distance distance);

}
