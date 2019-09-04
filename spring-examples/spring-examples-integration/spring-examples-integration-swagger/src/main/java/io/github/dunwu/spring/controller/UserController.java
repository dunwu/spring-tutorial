package io.github.dunwu.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.github.dunwu.spring.model.User;
import io.github.dunwu.spring.model.UserList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 本 REST 接口主要用于展示如何配合 swagger-ui 生成动态API平台。 返回的数据是模拟业务的数据
 * @author Zhang Peng
 */
@Api(value = "用户API", description = "用户API")
@RestController
@RequestMapping(value = "/user", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @ApiOperation(value = "查询用户列表", notes = "查询所有的用户信息", response = UserList.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public UserList getAllusers() {
        UserList users = new UserList();
        User user1 = new User(1L, "zhangsan", "zhangsan@163.com");
        User user2 = new User(2L, "lisi", "lisi@126.com");
        User user3 = new User(3L, "wangwu", "wangwu@163.com");
        users.getUsers().add(user1);
        users.getUsers().add(user2);
        users.getUsers().add(user3);
        return users;
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据用户Id查询用户信息", notes = "根据用户Id查询用户信息", response = User.class)
    public ResponseEntity getUserById(
            @ApiParam(name = "id", value = "用户编号", required = true) @PathVariable("id") Long id) {
        if (id <= 3) {
            User user = new User(1L, "zhangsan", "zhangsan@163.com");
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加用户", notes = "添加用户", response = String.class)
    public ResponseEntity add(@ApiParam(name = "name", value = "姓名", required = true) @RequestParam("name") String name,
            @ApiParam(name = "email", value = "姓名", required = true) @RequestParam("email") String email) {
        // 略去数据库操作：插入一条新记录
        logger.info("添加用户成功。name:{}, email:{}", name, email);
        return new ResponseEntity<>("add user success.", HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "根据用户Id删除用户信息", notes = "根据用户Id删除用户信息", response = String.class)
    public ResponseEntity<String> deleteByUserId(
            @ApiParam(name = "id", value = "用户编号", required = true) @PathVariable("id") Long id) {
        // 略去数据库操作：查找并删除一条存在记录
        logger.info("删除用户成功。id:{}", id);
        return new ResponseEntity<>("delete user success.", HttpStatus.OK);
    }
}
