package io.github.dunwu.springboot.data.mongodb.customer;

import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface to manage {@link Customer} instances.
 *
 * @author Oliver Gierke
 */
@Repository("customerRepository")
public interface CustomerRepository extends CrudRepository<Customer, String> {

    /**
     * Derived query using dynamic sort information.
     *
     * @param lastname
     * @param sort
     * @return
     */
    List<Customer> findByLastname(String lastname, Sort sort);

    /**
     * Show case for a repository query using geo-spatial functionality.
     *
     * @param point
     * @param distance
     * @return
     */
    GeoResults<Customer> findByAddressLocationNear(Point point, Distance distance);

}
