
package example.spring.data.nosql.elasticsearch.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "person")
public class Person {

    @Id
    private String id;

    private String name;

    private String address;

    private int age;

    public Person() { }

    public Person(String name, String address, int age) {
        this.name = name;
        this.address = address;
        this.age = age;
    }

}
