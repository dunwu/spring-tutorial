package example.spring.data.jdbc;

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
 *
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
        jdbcTemplate.update("INSERT INTO `t_user`(`name`, `age`, `address`, `email`) VALUES(?, ?, ?, ?)",
            user.getName(), user.getAge(), user.getAddress(), user.getEmail());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchInsert(List<User> users) {
        String sql = "INSERT INTO `t_user`(`name`, `age`, `address`, `email`) VALUES(?, ?, ?, ?)";

        List<Object[]> params = new ArrayList<>();

        users.forEach(user -> {
            params.add(new Object[] { user.getName(), user.getAge(), user.getAddress(), user.getEmail() });
        });
        jdbcTemplate.batchUpdate(sql, params);
    }

    @Override
    public void deleteByName(String name) {
        jdbcTemplate.update("DELETE FROM `t_user` WHERE `name` = ?", name);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll() {
        jdbcTemplate.execute("DELETE FROM `t_user`");
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update("UPDATE `t_user` SET `name`=?, `age`=?, `address`=?, `email`=? WHERE `id`=?",
            user.getName(), user.getAge(), user.getAddress(), user.getEmail(), user.getId());
    }

    @Override
    public Integer count() {
        try {
            return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM `t_user`", Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<User> list() {
        return jdbcTemplate.query("SELECT * FROM `t_user`", new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public User queryByName(String name) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM `t_user` WHERE `name` = ?",
                new BeanPropertyRowMapper<>(User.class), name);
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
        jdbcTemplate.execute("TRUNCATE TABLE `t_user`");
    }

    @Override
    public void recreateTable() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS `t_user`");

        String sqlStatement =
            "CREATE TABLE `t_user` (\n"
                + "    `id`      BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',\n"
                + "    `name`    VARCHAR(255)        NOT NULL DEFAULT '' COMMENT '用户名',\n"
                + "    `age`     INT(3)              NOT NULL DEFAULT 0 COMMENT '年龄',\n"
                + "    `address` VARCHAR(255)        NOT NULL DEFAULT '' COMMENT '地址',\n"
                + "    `email`   VARCHAR(255)        NOT NULL DEFAULT '' COMMENT '邮件',\n"
                + "    PRIMARY KEY (`id`),\n"
                + "    UNIQUE (`name`)\n"
                + ");";
        jdbcTemplate.execute(sqlStatement);
    }

}
