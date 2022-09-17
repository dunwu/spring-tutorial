package io.github.dunwu.springboot.web;

import com.google.common.collect.Iterables;
import io.github.dunwu.springboot.data.User;
import io.github.dunwu.springboot.data.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

@Api(tags = "用户管理")
@RequestMapping("user")
@RestController
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @ApiOperation("创建用户")
    @PostMapping("save")
    public ResponseDTO<User> save(@RequestBody @Valid User user) {
        User save = repository.save(user);
        return new ResponseDTO<>(true, ResponseDTO.CodeEn.SUCCESS, save);
    }

    @ApiIgnore
    @DeleteMapping("delete/{id}")
    public ResponseDTO deleteById(@PathVariable Long id) {
        repository.deleteById(id);
        return new ResponseDTO<>(true, ResponseDTO.CodeEn.SUCCESS, null);
    }

    @ApiOperation("根据ID查用户详情")
    @GetMapping("find/{id}")
    public ResponseDTO<User> findById(@PathVariable Long id) {
        Optional<User> optional = repository.findById(id);
        User user = optional.get();
        return new ResponseDTO<>(true, ResponseDTO.CodeEn.SUCCESS, user);
    }

    @ApiOperation("根据用户名查用户详情")
    @GetMapping("findByUsername")
    public ResponseDTO<User> findByUsername(@RequestParam("username") String username) {
        User user = repository.findByUsername(username);
        return new ResponseDTO<>(true, ResponseDTO.CodeEn.SUCCESS, user);
    }

    @ApiOperation("用户列表")
    @GetMapping("list")
    public ResponseDTO<List<User>> list() {
        Iterable<User> iterable = repository.findAll();
        List<User> users = new ArrayList<>();
        Iterables.addAll(users, iterable);
        return new ResponseDTO<>(true, ResponseDTO.CodeEn.SUCCESS, users);
    }

    @ApiOperation("用户列表分页展示")
    @GetMapping("page")
    public ResponseDTO<Page<User>> page(@ApiParam("查看第几页") @RequestParam int page,
        @ApiParam("每页多少条") @RequestParam int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<User> userPage = repository.findAll(pageRequest);
        return new ResponseDTO<>(true, ResponseDTO.CodeEn.SUCCESS, userPage);
    }

}
