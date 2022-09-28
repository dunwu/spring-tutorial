package io.github.dunwu.springboot.data.mongodb.customer;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

/**
 * An entity to represent a customer.
 *
 * @author Oliver Gierke
 */
@Data
@Document
public class Customer {

    private String id, firstname, lastname;
    private Address address;

    /**
     * Creates a new {@link Customer} with the given firstname and lastname.
     *
     * @param firstname must not be {@literal null} or empty.
     * @param lastname  must not be {@literal null} or empty.
     */
    public Customer(String firstname, String lastname) {

        Assert.hasText(firstname, "Firstname must not be null or empty!");
        Assert.hasText(lastname, "Lastname must not be null or empty!");

        this.firstname = firstname;
        this.lastname = lastname;
    }

}
