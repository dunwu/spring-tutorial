package example.spring.core.validation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 用户实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2023-01-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @NotNull
    private Long id;

    @NotBlank
    @Size(min = 2, max = 10)
    private String name;

    @Min(value = 1)
    @Max(value = 100)
    private Integer age;

}
