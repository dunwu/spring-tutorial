package example.spring.data.nosql.redis.quickstart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 4142994984277644695L;

    private Long id;
    private String name;
    private Integer age;
    private String address;
    private String email;

}
