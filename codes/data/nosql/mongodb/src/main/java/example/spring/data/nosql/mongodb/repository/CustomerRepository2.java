package example.spring.data.nosql.mongodb.repository;

import example.spring.data.nosql.mongodb.entity.Customer2;
import example.spring.data.nosql.mongodb.entity.CustomerDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Sample repository managing customers to show projecting functionality of Spring Data MongoDB.
 *
 * @author Oliver Gierke
 */
@Repository("customerRepository2")
public interface CustomerRepository2 extends CrudRepository<Customer2, ObjectId> {

    /**
     * Uses a projection interface to indicate the fields to be returned. As the projection doesn't use any dynamic
     * fields, the query execution will be restricted to only the fields needed by the projection.
     */
    Collection<CustomerProjection> findAllProjectedBy();

    /**
     * When a projection is used that contains dynamic properties (i.e. SpEL expressions in an {@link Value}
     * annotation), the normal target entity will be loaded but dynamically projected so that the target can be referred
     * to in the expression.
     */
    Collection<CustomerSummary> findAllSummarizedBy();

    /**
     * Uses a concrete DTO type to indicate the fields to be returned. This will cause the original object being loaded
     * and the properties copied over into the DTO.
     */
    Collection<CustomerDto> findAllDtoedBy();

    public Customer2 findByFirstName(String firstName);

    public List<Customer2> findByLastName(String lastName);

    /**
     * Passes in the projection type dynamically (either interface or DTO).
     */
    <T> Collection<T> findByFirstName(String firstName, Class<T> projection);

    /**
     * Projection for a single entity.
     */
    CustomerProjection findProjectedById(ObjectId id);

    /**
     * Dynamic projection for a single entity.
     */
    <T> T findProjectedById(ObjectId id, Class<T> projection);

    /**
     * Projections used with pagination.
     */
    Page<CustomerProjection> findPagedProjectedBy(Pageable pageable);

}
