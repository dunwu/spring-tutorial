package io.github.dunwu.spring.data;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * user 表 Dao 接口的 Mysql 实现
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-10-12
 */
@Repository("mysqlUserDao")
public class MysqlUserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public MysqlUserDaoImpl(@Qualifier("mysqlJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(User user) {
        jdbcTemplate.update("INSERT INTO user(username, password, email) VALUES(?, ?, ?)",
            user.getUsername(), user.getPassword(), user.getEmail());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchInsert(List<User> users) {
        String sql = "INSERT INTO user(username, password, email) VALUES(?, ?, ?)";

        List<Object[]> params = new ArrayList<>();

        users.forEach(item -> {
            params.add(new Object[] { item.getUsername(), item.getPassword(), item.getEmail() });
        });
        jdbcTemplate.batchUpdate(sql, params);
    }

    @Override
    public void deleteByName(String username) {
        jdbcTemplate.update("DELETE FROM user WHERE username = ?", username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll() {
        jdbcTemplate.execute("DELETE FROM user");
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update("UPDATE USER SET username=?, password=?, email=? WHERE id=?",
            user.getUsername(), user.getPassword(), user.getEmail(), user.getId());
    }

    @Override
    public Integer count() {
        try {
            return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user", Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<User> list() {
        return jdbcTemplate.query("select * from USER", new BeanPropertyRowMapper(User.class));
    }

    @Override
    public User queryByName(String username) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM user WHERE username = ?",
                new BeanPropertyRowMapper<>(User.class), username);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public void truncate() {
        jdbcTemplate.execute("TRUNCATE TABLE user");
    }

    @Override
    public void recreateTable() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS user");

        String sqlStatement =
            "CREATE TABLE `user` (\n"
                + "    `id`       INT(20)             NOT NULL AUTO_INCREMENT COMMENT 'ID',\n"
                + "    `username` VARCHAR(30)         NOT NULL COMMENT '用户名',\n"
                + "    `password` VARCHAR(60)         NOT NULL COMMENT '密码',\n"
                + "    `email`    VARCHAR(100)        NOT NULL COMMENT '邮箱',\n"
                + "    PRIMARY KEY (`id`),\n"
                + "    UNIQUE KEY `uk_username`(`username`),\n"
                + "    UNIQUE KEY `uk_email`(`email`)\n"
                + ") ENGINE = INNODB COMMENT ='用户表';\n";
        jdbcTemplate.execute(sqlStatement);
    }

}
