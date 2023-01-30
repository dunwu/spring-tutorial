package example.spring.data.nosql.mongodb.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

@Data
@Document(value = "customer3")
public class Customer3 {

    @Id
    public String id;

    public String firstName;

    public String lastName;

    private Address address;

    /**
     * Creates a new {@link Customer3} with the given firstName and lastName.
     *
     * @param firstName must not be {@literal null} or empty.
     * @param lastName  must not be {@literal null} or empty.
     */
    public Customer3(String firstName, String lastName) {

        Assert.hasText(firstName, "Firstname must not be null or empty!");
        Assert.hasText(lastName, "Lastname must not be null or empty!");

        this.firstName = firstName;
        this.lastName = lastName;
    }

}
