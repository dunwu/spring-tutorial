package io.github.dunwu.spring.controller;

import io.github.dunwu.spring.model.User;
import io.github.dunwu.spring.model.UserList;
import io.github.dunwu.tool.core.constant.enums.ResultStatus;
import io.github.dunwu.tool.data.DataListResult;
import io.github.dunwu.tool.data.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 本 REST 接口主要用于展示如何配合 swagger-ui 生成动态API平台。 返回的数据是模拟业务的数据
 *
 * @author Zhang Peng
 */
@Api(value = "user")
@RestController
@RequestMapping(value = "user", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping(value = "list")
    @ApiOperation(value = "查询用户列表", notes = "查询所有的用户信息", response = UserList.class)
    public DataListResult<User> list() {
        List<User> users = new ArrayList<>();
        User user1 = new User(1L, "zhangsan", "zhangsan@163.com");
        User user2 = new User(2L, "lisi", "lisi@126.com");
        User user3 = new User(3L, "wangwu", "wangwu@163.com");
        users.add(user1);
        users.add(user2);
        users.add(user3);
        return DataListResult.ok(users);
    }

    @GetMapping(value = "get/{id}")
    @ApiOperation(value = "根据用户Id查询用户信息", notes = "根据用户Id查询用户信息", response = User.class)
    public DataResult<User> getUserById(
        @ApiParam(name = "id", value = "用户编号", required = true) @PathVariable("id") Long id) {
        if (id > 0) {
            User user = new User(1L, "zhangsan", "zhangsan@163.com");
            return DataResult.ok(user);
        }
        return DataResult.fail(ResultStatus.DATA_ERROR);
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加用户", notes = "添加用户", response = String.class)
    public DataResult<Boolean> add(
        @ApiParam(name = "name", value = "姓名", required = true) @RequestParam("name") String name,
        @ApiParam(name = "email", value = "姓名", required = true) @RequestParam("email") String email) {
        // 略去数据库操作：插入一条新记录
        logger.info("添加用户成功。name:{}, email:{}", name, email);
        return DataResult.ok();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "根据用户Id删除用户信息", notes = "根据用户Id删除用户信息", response = String.class)
    public DataResult<Boolean> deleteByUserId(
        @ApiParam(name = "id", value = "用户编号", required = true) @PathVariable("id") Long id) {
        // 略去数据库操作：查找并删除一条存在记录
        logger.info("删除用户成功。id:{}", id);
        return DataResult.ok();
    }

}
