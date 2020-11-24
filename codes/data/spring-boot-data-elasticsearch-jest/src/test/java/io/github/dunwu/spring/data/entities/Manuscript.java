package io.github.dunwu.spring.data.entities;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Data
@ToString
public class Manuscript {

    private String title;

    private String abstractText;

    @Field(type = FieldType.Text, index = false)
    private String status;

    @Field(type = FieldType.Nested)
    private List<Role> role;

}
