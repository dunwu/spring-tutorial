package example.spring.core.validation.entity;

import example.spring.core.validation.annotation.AddCheck;
import example.spring.core.validation.annotation.EditCheck;
import example.spring.core.validation.annotation.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
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
public class User3 {

    @NotNull(groups = EditCheck.class)
    private Long id;

    @NotNull(groups = { AddCheck.class, EditCheck.class })
    @Size(min = 2, max = 10, groups = { AddCheck.class, EditCheck.class })
    private String name;

    @IsMobile(message = "不是有效手机号", groups = { AddCheck.class, EditCheck.class })
    private String mobile;

    @Valid
    @NotNull(groups = { AddCheck.class, EditCheck.class })
    private Job job;

    @Data
    public static class Job {

        @Min(value = 1, groups = EditCheck.class)
        private Long id;

        @NotBlank(groups = { AddCheck.class, EditCheck.class })
        @Length(min = 2, max = 10, groups = { AddCheck.class, EditCheck.class })
        private String name;

        @NotBlank(groups = { AddCheck.class, EditCheck.class })
        @Length(min = 2, max = 10, groups = { AddCheck.class, EditCheck.class })
        private String level;

    }

}
