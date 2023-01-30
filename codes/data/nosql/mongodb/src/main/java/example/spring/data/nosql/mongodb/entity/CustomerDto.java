package example.spring.data.nosql.mongodb.entity;

import lombok.Value;

/**
 * A sample DTO only containing the firstName.
 *
 * @author Oliver Gierke
 */
@Value
public class CustomerDto {

    String firstName;

}
