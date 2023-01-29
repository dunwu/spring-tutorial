package io.github.dunwu.springboot.data.mongodb.entity;

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
