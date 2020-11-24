package io.github.dunwu.spring.data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import javax.sql.DataSource;

@Slf4j
@SpringBootApplication
public class SpringBootDataJdbcApplication implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public SpringBootDataJdbcApplication(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDataJdbcApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        DataSource dataSource = jdbcTemplate.getDataSource();

        Connection connection;
        if (dataSource != null) {
            connection = dataSource.getConnection();
        } else {
            log.error("Get dataSource failed!");
            return;
        }

        if (connection != null) {
            log.info("DataSource Url: {}", connection.getMetaData().getURL());
        } else {
            log.error("Connect to datasource failed!");
        }
    }

}
