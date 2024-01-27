# spring-boot-swagger

> **本项目演示 Spring Boot + Swagger 生成 REST API 接口文档。**
>
> 本项目使用 [**`swagger-spring-boot-starter`**](https://github.com/SpringForAll/spring-boot-starter-swagger) 管理 swagger 以生成定制化的 REST API 接口文档。
>
> 项目示例中 `UserController` 定义了 `/user/*` REST 接口去调用 JPA 做 CRUD 操作。如果对 Spring Boot + JPA 不了解，可以先学习 [spring-boot-data-jpa](https://github.com/dunwu/spring-boot-tutorial/tree/master/codes/spring-boot-data-jpa) 项目。
>
> 依赖环境：
>
> ![h2](https://img.shields.io/badge/h2-1.4.199-blue) ![jdk](https://img.shields.io/badge/jdk-1.8.0__181-blue) ![maven](https://img.shields.io/badge/maven-v3.6.0-blue)

## 使用说明

### 构建

在项目根目录下执行 maven 指令。

```
mvn clean package -DskipTests=true
```

### Swagger API

使用 Swagger 生成 REST API 文档，需要你在项目代码中使用 Swagger 注解，标记你想要展示的 API 接口以及传输实体。

下面给出比较简单的示例：

#### 实体

```java
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户基本信息")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(name = "ID", hidden = true, example = "0")
	private Long id;

	@NotNull
	@Column(unique = true)
	@Length(min = 4, max = 30)
	@ApiModelProperty(name = "用户名", example = "user")
	private String username;

	@NotNull
	@Length(min = 6, max = 60)
	@ApiModelProperty(name = "密码", example = "123456")
	private String password;

	@NotNull
	@Email
	@Column(unique = true)
	@ApiModelProperty(name = "用户名", example = "xxx@xxx.com")
	private String email;
  
  // ...
}
```

#### Api

```java

import io.github.dunwu.springboot.core.data.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Api(tags = "用户管理")
@RequestMapping("user")
@RestController
public class UserController {

    // ...

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

    @ApiOperation("用户列表分页展示")
    @GetMapping("page")
    public ResponseDTO<Page<User>> page(@ApiParam("查看第几页") @RequestParam int page,
        @ApiParam("每页多少条") @RequestParam int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<User> userPage = repository.findAll(pageRequest);
        return new ResponseDTO<>(true, ResponseDTO.CodeEn.SUCCESS, userPage);
    }

    // ...
}
```



### 配置

在 application.properties 中添加 swagger 配置。以下是一份基本的示例配置。更多详细配置信息，可以参考： [**`swagger-spring-boot-starter`**](https://github.com/SpringForAll/spring-boot-starter-swagger) 

```properties
swagger.enabled = true
swagger.title = spring-boot-swagger
swagger.description = spring-boot-swagger
swagger.license = Apache License, Version 2.0
swagger.licenseUrl = https://www.apache.org/licenses/LICENSE-2.0.html
swagger.termsOfServiceUrl = https://github.com/dunwu
swagger.contact.name = Dunwu
swagger.contact.url = https://github.com/dunwu
swagger.contact.email = forbreak@163.com
swagger.base-package = io.github.dunwu.springboot
swagger.apply-default-response-messages = true
```

### 启动

```
cd target
java -jar spring-boot-swagger-1.0.0.jar
```

### 访问

打开浏览器，访问  http://localhost:8080/swagger-ui.html 可以看到自动生成的 REST API 文档。

![image-20191119172610814](https://raw.githubusercontent.com/dunwu/images/master/snap/image-20191119172610814.png)

## 参考资料

- [swagger 官方文档](http://swagger.io/)
- [swagger-spring-boot-starter](https://github.com/SpringForAll/spring-boot-starter-swagger)
