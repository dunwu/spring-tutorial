package example.spring.data.orm.mybatis;

import example.spring.data.orm.mybatis.entity.User;
import example.spring.data.orm.mybatis.mapper.UserMapper;
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
@MapperScan("example.spring.data.orm.mybatis.mapper")
public class MybatisPlusApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(MybatisPlusApplication.class);

    private final UserMapper userMapper;

    public MybatisPlusApplication(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusApplication.class, args);
    }

    @Override
    public void run(String... args) {

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
