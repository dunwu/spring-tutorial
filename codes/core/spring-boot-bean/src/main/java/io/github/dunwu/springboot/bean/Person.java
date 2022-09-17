package io.github.dunwu.springboot.bean;

import lombok.*;

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
