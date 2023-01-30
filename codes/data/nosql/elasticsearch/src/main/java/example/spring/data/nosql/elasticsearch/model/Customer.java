
package example.spring.data.nosql.elasticsearch.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "spring_tutorial_customer")
public class Customer {

    @Id
    private String id;

    private String name;

    private String address;

    private int age;

    public Customer() { }

    public Customer(String name, String address, int age) {
        this.name = name;
        this.address = address;
        this.age = age;
    }

}
