package example.spring.core.validation.web;

import cn.hutool.json.JSONUtil;
import example.spring.core.validation.annotation.AddCheck;
import example.spring.core.validation.annotation.EditCheck;
import example.spring.core.validation.entity.User2;
import io.github.dunwu.tool.data.DataResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 最基本的 Spring 校验
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-12-26
 */
@Slf4j
@Validated
@RestController
@RequestMapping("validate2")
public class ValidatorController2 {

    /**
     * {@link RequestBody} 参数校验
     */
    @PostMapping(value = "add")
    public DataResult<Boolean> add(@Validated(AddCheck.class) @RequestBody User2 entity) {
        log.info("添加一条记录：{}", JSONUtil.toJsonStr(entity));
        return DataResult.ok(true);
    }

    /**
     * {@link RequestBody} 参数校验
     */
    @PostMapping(value = "edit")
    public DataResult<Boolean> edit(@Validated(EditCheck.class) @RequestBody User2 entity) {
        log.info("编辑一条记录：{}", JSONUtil.toJsonStr(entity));
        return DataResult.ok(true);
    }

}
