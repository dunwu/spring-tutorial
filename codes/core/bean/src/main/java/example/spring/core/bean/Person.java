package example.spring.core.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Lombok 示例
 *
 * @author Zhang Peng
 * @see <a href= "http://jnb.ociweb.com/jnb/jnbJan2010.html">http://jnb.ociweb.com/jnb/jnbJan2010.html</a>
 */
@Data
@ToString(exclude = "age")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = { "age", "sex" })
public class Person {

    private String name;

    private Integer age;

    private String sex;

}
