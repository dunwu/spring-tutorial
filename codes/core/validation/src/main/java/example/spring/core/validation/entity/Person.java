package example.spring.core.validation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2023-01-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Serializable {

    private Long id;

    private String name;

    private Integer age;

}
