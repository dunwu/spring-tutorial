package io.github.dunwu.spring.data.entities;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Data
@ToString
@Document(indexName = "test-contact-test", type = "contact-test-type", shards = 1, replicas = 0)
public class Contact {

    @Id
    private String id;

    private String name;

    @Field(type = FieldType.Nested)
    private List<Manuscript> manuscripts;

}
