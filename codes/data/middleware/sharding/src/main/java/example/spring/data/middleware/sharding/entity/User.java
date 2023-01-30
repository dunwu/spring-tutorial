package example.spring.data.middleware.sharding.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {

    private Long id;

    private String name;

    private Integer age;

    private String address;

    private String email;

    public User() {
    }

    public User(String name, Integer age, String address, String email) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.email = email;
    }

}
