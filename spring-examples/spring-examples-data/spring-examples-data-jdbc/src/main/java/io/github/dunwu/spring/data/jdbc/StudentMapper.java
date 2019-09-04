package io.github.dunwu.spring.data.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class StudentMapper implements RowMapper<StudentDTO> {
    @Override
    public StudentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        StudentDTO student = new StudentDTO();
        student.setId(rs.getInt("id"));
        student.setName(rs.getString("name"));
        student.setAge(rs.getInt("age"));
        return student;
    }
}
