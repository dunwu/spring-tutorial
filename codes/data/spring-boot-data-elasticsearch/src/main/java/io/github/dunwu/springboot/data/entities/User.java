package io.github.dunwu.springboot.data.entities;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@ToString
@Document(indexName = "user", type = "user", shards = 1, replicas = 0, refreshInterval = "-1")
public class User {

    @Id
    private String id;

    private String username;

    private int age;

    private String password;

    @Field(type = FieldType.Text, fielddata = true)
    private String email;

    public User() {}

    public User(String username, int age, String password, String email) {
        this.username = username;
        this.age = age;
        this.password = password;
        this.email = email;
    }

    public User(String id, String username, int age, String password, String email) {
        this.id = id;
        this.username = username;
        this.age = age;
        this.password = password;
        this.email = email;
    }

}
