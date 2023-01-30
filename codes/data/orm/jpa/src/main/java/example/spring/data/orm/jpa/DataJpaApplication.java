package example.spring.data.orm.jpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

@Slf4j
// 【可选】指定扫描的 Entity 目录，如果不指定，会扫描全部目录
@EntityScan("example.spring.data.orm.jpa")
// 【可选】指定扫描的 Repository 目录，如果不指定，会扫描全部目录
@EnableJpaRepositories(basePackages = { "example.spring.data.orm.jpa" })
// 【可选】开启 JPA auditing 能力，可以自动赋值一些字段，比如创建时间、最后一次修改时间等等
@EnableJpaAuditing
@SpringBootApplication
public class DataJpaApplication implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public DataJpaApplication(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(DataJpaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        printDataSourceInfo(jdbcTemplate);
    }

    private void printDataSourceInfo(JdbcTemplate jdbcTemplate) throws SQLException {

        DataSource dataSource = jdbcTemplate.getDataSource();

        Connection connection;
        if (dataSource != null) {
            connection = dataSource.getConnection();
        } else {
            log.error("连接数据源失败！");
            return;
        }

        if (connection != null) {
            log.info("Connection 类：{}", connection.getClass().getTypeName());
            log.info("连接数据源成功！数据源 Url: {}", connection.getMetaData().getURL());
        } else {
            log.error("连接数据源失败！");
        }
    }

}
