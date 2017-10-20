package io.github.dunwu.spring.data.jdbc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class MyJdbcTemplateImpl implements StudentDAO {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(String name, Integer age) {
        String sql = "insert into Student (name, age) values (?, ?)";
        jdbcTemplate.update(sql, name, age);
        logger.debug("Created Record Name = " + name + " Age = " + age);
    }

    @Override
    public StudentDTO getById(Integer id) {
        String sql = "select * from Student where id = ?";
        StudentDTO student =
                        jdbcTemplate.queryForObject(sql, new Object[] {id}, new StudentMapper());
        logger.debug("查到Student记录：{}", student.toString());
        return student;
    }

    @Override
    public List<StudentDTO> list() {
        String sql = "select * from Student";
        List<StudentDTO> students = jdbcTemplate.query(sql, new StudentMapper());
        return students;
    }

    @Override
    public void delete(Integer id) {
        String sql = "delete from Student where id = ?";
        jdbcTemplate.update(sql, id);
        logger.debug("Deleted Record with ID = " + id);
    }

    @Override
    public void update(Integer id, Integer age) {
        String sql = "update Student set age = ? where id = ?";
        jdbcTemplate.update(sql, age, id);
        logger.debug("Updated Record with ID = " + id);
    }
}
