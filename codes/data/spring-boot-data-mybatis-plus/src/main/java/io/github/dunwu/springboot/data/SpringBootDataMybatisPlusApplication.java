package io.github.dunwu.springboot.data;

import io.github.dunwu.springboot.data.entity.User;
import io.github.dunwu.springboot.data.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-10-12
 */
@SpringBootApplication
@MapperScan("io.github.dunwu.springboot.data.mapper")
public class SpringBootDataMybatisPlusApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SpringBootDataMybatisPlusApplication.class);

    private final UserMapper userMapper;

    public SpringBootDataMybatisPlusApplication(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDataMybatisPlusApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        if (userMapper == null) {
            log.error("连接数据源失败");
            return;
        }

        List<User> userList = userMapper.selectList(null);
        log.info("======= 打印 user 表所有数据 =======");
        userList.forEach(user -> {
            log.info(user.toString());
        });
    }

}
