package io.github.dunwu.springboot.data.mongodb.projections;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Sample repository managing customers to show projecting functionality of Spring Data MongoDB.
 * @author Oliver Gierke
 */
@Repository("projectionsCustomerRepository")
interface CustomerRepository extends CrudRepository<Customer, ObjectId> {

    /**
     * Uses a projection interface to indicate the fields to be returned. As the projection doesn't use any dynamic
     * fields, the query execution will be restricted to only the fields needed by the projection.
     * @return
     */
    Collection<CustomerProjection> findAllProjectedBy();

    /**
     * When a projection is used that contains dynamic properties (i.e. SpEL expressions in an {@link Value}
     * annotation), the normal target entity will be loaded but dynamically projected so that the target can be referred
     * to in the expression.
     * @return
     */
    Collection<CustomerSummary> findAllSummarizedBy();

    /**
     * Uses a concrete DTO type to indicate the fields to be returned. This will cause the original object being loaded
     * and the properties copied over into the DTO.
     * @return
     */
    Collection<CustomerDto> findAllDtoedBy();

    /**
     * Passes in the projection type dynamically (either interface or DTO).
     * @param firstname
     * @param projection
     * @return
     */
    <T> Collection<T> findByFirstname(String firstname, Class<T> projection);

    /**
     * Projection for a single entity.
     * @param id
     * @return
     */
    CustomerProjection findProjectedById(ObjectId id);

    /**
     * Dynamic projection for a single entity.
     * @param id
     * @param projection
     * @return
     */
    <T> T findProjectedById(ObjectId id, Class<T> projection);

    /**
     * Projections used with pagination.
     * @param pageable
     * @return
     */
    Page<CustomerProjection> findPagedProjectedBy(Pageable pageable);

}
