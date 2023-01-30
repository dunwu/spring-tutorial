package example.spring.data.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

@Slf4j
@SpringBootApplication
public class JdbcMultiDataSourceApplication implements CommandLineRunner {

    private final UserDao mysqlUserDao;

    private final UserDao h2UserDao;

    public JdbcMultiDataSourceApplication(@Qualifier("mysqlUserDao") UserDao mysqlUserDao,
        @Qualifier("h2UserDao") UserDao h2UserDao) {
        this.mysqlUserDao = mysqlUserDao;
        this.h2UserDao = h2UserDao;
    }

    public static void main(String[] args) {
        SpringApplication.run(JdbcMultiDataSourceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        if (mysqlUserDao != null && mysqlUserDao.getJdbcTemplate() != null) {
            printDataSourceInfo(mysqlUserDao.getJdbcTemplate());
        } else {
            log.error("连接数据源失败！");
            return;
        }

        if (h2UserDao != null) {
            printDataSourceInfo(h2UserDao.getJdbcTemplate());
        } else {
            log.error("连接数据源失败！");
            return;
        }

        // 主数据源执行 JDBC SQL
        mysqlUserDao.recreateTable();

        // 次数据源执行 JDBC SQL
        h2UserDao.recreateTable();
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
