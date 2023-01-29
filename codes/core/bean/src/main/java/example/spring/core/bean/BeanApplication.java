package example.spring.core.bean;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class BeanApplication {

    private final BeanUtil beanUtil;

    public static void main(String[] args) {
        SpringApplication.run(BeanApplication.class, args);
    }

    @PostConstruct
    public void postConstruct() {
        User[] users = { new User(1L, "userA", "用户A"), new User(2L, "userB", "用户B"),
            new User(3L, "userC", "用户C"), new User(4L, "userD", "用户D") };
        List<UserDto> list = beanUtil.mapList(Arrays.asList(users), UserDto.class);
        log.info("List<User> 转化为 List<UserDto> 的结果: {}", list);
    }

}
