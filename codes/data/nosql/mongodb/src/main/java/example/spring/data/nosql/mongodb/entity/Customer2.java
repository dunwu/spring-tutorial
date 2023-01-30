package example.spring.data.nosql.mongodb.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Oliver Gierke
 * @author Mark Paluch
 */
@Data
@Document(value = "customer2")
public class Customer2 {

    @Id
    ObjectId id = new ObjectId();
    String firstName;
    String lastName;

    public Customer2(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
