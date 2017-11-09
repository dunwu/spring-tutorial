package io.github.dunwu.spring.libs.lombok;

import lombok.Data;
import lombok.ToString;

/**
 * @author Zhang Peng
 */
@Data
@ToString(exclude = "age")
public class Person {
    private String name;
    private Integer age;
    private String sex;
}
