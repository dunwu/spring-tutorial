package io.github.dunwu.springboot.data;

import cn.hutool.json.JSONUtil;
import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

@Slf4j
@SpringBootApplication
public class DataJdbcDruidApplication implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public DataJdbcDruidApplication(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(DataJdbcDruidApplication.class, args);
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
            if (dataSource instanceof DruidDataSource) {
                DruidDataSource druidDataSource = (DruidDataSource) dataSource;
                log.info("Druid 连接配置：\n{}", JSONUtil.toJsonPrettyStr(druidDataSource));
            }
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
