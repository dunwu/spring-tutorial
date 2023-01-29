package example.spring.core.validation.web;

import cn.hutool.json.JSONUtil;
import example.spring.core.validation.entity.User;
import io.github.dunwu.tool.data.DataResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 最基本的 Spring 校验
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-12-26
 */
@Slf4j
@Validated
@RestController
@RequestMapping("validate1")
public class ValidatorController {

    /**
     * {@link RequestBody} 参数校验
     */
    @PostMapping(value = "save")
    public DataResult<Boolean> save(@Valid @RequestBody User entity) {
        log.info("保存一条记录：{}", JSONUtil.toJsonStr(entity));
        return DataResult.ok(true);
    }

    /**
     * {@link RequestParam} 参数校验
     */
    @GetMapping(value = "queryByName")
    public DataResult<User> queryByName(
        @RequestParam("username")
        @NotBlank
        @Size(min = 2, max = 10)
        String name
    ) {
        User user = new User(1L, name, 18);
        return DataResult.ok(user);
    }

    /**
     * {@link PathVariable} 参数校验
     */
    @GetMapping(value = "detail/{id}")
    public DataResult<User> detail(@PathVariable("id") @Min(1L) Long id) {
        User user = new User(id, "李四", 18);
        return DataResult.ok(user);
    }

}
