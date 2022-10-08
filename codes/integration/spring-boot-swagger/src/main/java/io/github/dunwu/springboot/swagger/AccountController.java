package io.github.dunwu.springboot.swagger;

import com.google.common.collect.Iterables;
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
@RequestMapping("account")
@RestController
public class AccountController {

    private final AccountRepository repository;

    public AccountController(AccountRepository repository) {
        this.repository = repository;
    }

    @ApiOperation("创建用户")
    @PostMapping("save")
    public ResponseDto<Account> save(@RequestBody @Valid Account user) {
        Account save = repository.save(user);
        return new ResponseDto<>(true, ResponseDto.CodeEn.SUCCESS, save);
    }

    @ApiIgnore
    @DeleteMapping("delete/{id}")
    public ResponseDto deleteById(@PathVariable Long id) {
        repository.deleteById(id);
        return new ResponseDto<>(true, ResponseDto.CodeEn.SUCCESS, null);
    }

    @ApiOperation("根据ID查用户详情")
    @GetMapping("find/{id}")
    public ResponseDto<Account> findById(@PathVariable Long id) {
        Optional<Account> optional = repository.findById(id);
        Account user = optional.get();
        return new ResponseDto<>(true, ResponseDto.CodeEn.SUCCESS, user);
    }

    @ApiOperation("根据用户名查用户详情")
    @GetMapping("findByUsername")
    public ResponseDto<Account> findByUsername(@RequestParam("username") String username) {
        Account user = repository.findByUsername(username);
        return new ResponseDto<>(true, ResponseDto.CodeEn.SUCCESS, user);
    }

    @ApiOperation("用户列表")
    @GetMapping("list")
    public ResponseDto<List<Account>> list() {
        Iterable<Account> iterable = repository.findAll();
        List<Account> users = new ArrayList<>();
        Iterables.addAll(users, iterable);
        return new ResponseDto<>(true, ResponseDto.CodeEn.SUCCESS, users);
    }

    @ApiOperation("用户列表分页展示")
    @GetMapping("page")
    public ResponseDto<Page<Account>> page(@ApiParam("查看第几页") @RequestParam int page,
        @ApiParam("每页多少条") @RequestParam int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Account> userPage = repository.findAll(pageRequest);
        return new ResponseDto<>(true, ResponseDto.CodeEn.SUCCESS, userPage);
    }

}
