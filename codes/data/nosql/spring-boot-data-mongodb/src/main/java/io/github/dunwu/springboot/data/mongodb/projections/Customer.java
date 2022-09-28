package io.github.dunwu.springboot.data.mongodb.projections;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Oliver Gierke
 * @author Mark Paluch
 */
@Data
@Document
class Customer {

    @Id
    ObjectId id = new ObjectId();
    String firstname, lastname;

    public Customer(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

}
