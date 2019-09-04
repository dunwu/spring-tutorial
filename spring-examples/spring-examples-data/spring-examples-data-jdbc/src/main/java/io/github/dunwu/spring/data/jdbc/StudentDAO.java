package io.github.dunwu.spring.data.jdbc;

import java.util.List;

public interface StudentDAO {
    /**
     * This is the method to be used to create a record in the Student table.
     */
    void create(String name, Integer age);

    /**
     * This is the method to be used to list down a record from the Student table corresponding to a
     * passed student id.
     */
    StudentDTO getById(Integer id);

    /**
     * This is the method to be used to list down all the records from the Student table.
     */
    List<StudentDTO> list();

    /**
     * This is the method to be used to delete a record from the Student table corresponding to a
     * passed student id.
     */
    void delete(Integer id);

    /**
     * This is the method to be used to update a record into the Student table.
     */
    void update(Integer id, Integer age);
}
