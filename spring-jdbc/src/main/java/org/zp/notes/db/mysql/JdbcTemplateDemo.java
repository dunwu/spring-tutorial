package org.zp.notes.db.mysql;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcTemplateDemo implements StudentDAO {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(String name, Integer age) {
        String SQL = "insert into Student (name, age) values (?, ?)";
        jdbcTemplate.update(SQL, name, age);
        System.out.println("Created Record Name = " + name + " Age = " + age);
    }

    public StudentDTO getById(Integer id) {
        String SQL = "select * from Student where id = ?";
        StudentDTO student = jdbcTemplate.queryForObject(SQL, new Object[] {id}, new StudentMapper());
        return student;
    }

    public List<StudentDTO> list() {
        String SQL = "select * from Student";
        List<StudentDTO> students = jdbcTemplate.query(SQL, new StudentMapper());
        return students;
    }

    public void delete(Integer id) {
        String SQL = "delete from Student where id = ?";
        jdbcTemplate.update(SQL, id);
        System.out.println("Deleted Record with ID = " + id);
    }

    public void update(Integer id, Integer age) {
        String SQL = "update Student set age = ? where id = ?";
        jdbcTemplate.update(SQL, age, id);
        System.out.println("Updated Record with ID = " + id);
    }
}
