package io.github.dunwu.spring.data.entities;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@ToString
@Document(indexName = "product", type = "product", shards = 1, replicas = 0, refreshInterval = "-1")
public class Product {

    @Id
    private String id;

    private String name;

    private String description;

    private boolean enabled;

    public Product(String id, String name, String description, boolean enabled) {
        this();
        this.id = id;
        this.name = name;
        this.description = description;
        this.enabled = enabled;
    }

    public Product() {}

}
