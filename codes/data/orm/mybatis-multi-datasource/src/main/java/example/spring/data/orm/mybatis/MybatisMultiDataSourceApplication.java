package example.spring.data.orm.mybatis;

import example.spring.data.orm.mybatis.entity.User;
import example.spring.data.orm.mybatis.mapper.Db1UserMapper;
import example.spring.data.orm.mybatis.mapper.Db2UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-10-12
 */
@Slf4j
@SpringBootApplication
@MapperScan("example.spring.data.orm.mybatis.mapper")
public class MybatisMultiDataSourceApplication implements CommandLineRunner {

    private final Db1UserMapper db1UserMapper;

    private final Db2UserMapper db2UserMapper;

    public MybatisMultiDataSourceApplication(Db1UserMapper db1UserMapper, Db2UserMapper db2UserMapper) {
        this.db1UserMapper = db1UserMapper;
        this.db2UserMapper = db2UserMapper;
    }

    public static void main(String[] args) {
        SpringApplication.run(MybatisMultiDataSourceApplication.class, args);
    }

    @Override
    public void run(String... args) {

        if (db1UserMapper == null) {
            log.error("连接 DB1 数据源失败");
            return;
        } else {
            log.info("连接 DB1 数据源成功");
        }

        List<User> userList = db1UserMapper.selectList(null);
        log.info("======= 打印 user 表所有数据 =======");
        userList.forEach(user -> {
            log.info(user.toString());
        });

        if (db2UserMapper == null) {
            log.error("连接 DB2 数据源失败");
            return;
        } else {
            log.info("连接 DB2 数据源成功");
        }

        List<User> userList2 = db2UserMapper.selectList(null);
        log.info("======= 打印 user 表所有数据 =======");
        userList2.forEach(user -> {
            log.info(user.toString());
        });
    }

}
