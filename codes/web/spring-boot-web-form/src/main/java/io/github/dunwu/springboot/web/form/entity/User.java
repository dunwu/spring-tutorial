package io.github.dunwu.springboot.web.form.entity;

import io.github.dunwu.springboot.web.form.validator.annotation.Mobile;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.*;

/**
 * 用户校验对象
 * <p>
 * 注：{@link Mobile} 为自定义校验注解
 */
@Data
@ToString
public class User {

    @NotBlank
    @Size(min = 2, max = 30)
    private String name;

    @NotNull
    @Min(18)
    @Max(100)
    private Integer age;

    /**
     * 正则校验（必须是中文字符）
     */
    @Pattern(regexp = "[\u4E00-\u9FFF\\w]+")
    private String nickname;

    @Email
    private String email;

    @Mobile
    private String mobile;

    @Positive
    private Integer income;

}
