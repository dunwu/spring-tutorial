package io.github.dunwu.springboot.data.entities;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Data
@ToString
@Document(indexName = "person", type = "user", shards = 1, replicas = 0, refreshInterval = "-1")
public class Person {

    @Id
    private String id;

    private String name;

    @Field(type = FieldType.Nested)
    private List<Car> car;

}
