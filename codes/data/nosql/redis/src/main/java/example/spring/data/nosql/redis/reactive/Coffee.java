package example.spring.data.nosql.redis.reactive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coffee {

    private String id;
    private String name;

}
