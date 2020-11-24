package io.github.dunwu.spring.data.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class JdbcDemo {

    private static final Logger log = LoggerFactory.getLogger(JdbcDemo.class);

    public static ApplicationContext getH2ApplicationContext() {
        return new ClassPathXmlApplicationContext("/data/spring-h2.xml");
    }

    public static ApplicationContext getMysqlApplicationContext() {
        return new ClassPathXmlApplicationContext("/data/spring-mysql.xml");
    }

    public static ApplicationContext getDruidApplicationContext() {
        return new ClassPathXmlApplicationContext("/data/spring-druid.xml");
    }

    public static void execJdbcOper(MyJdbcTemplateImpl jdbcTemplateDemo) {
        log.debug("------Records Creation--------");
        jdbcTemplateDemo.create("Zara", 11);
        jdbcTemplateDemo.create("Nuha", 2);
        jdbcTemplateDemo.create("Ayan", 15);

        log.debug("------Listing Multiple Records--------");
        int lastId = 0;
        List<StudentDTO> students = jdbcTemplateDemo.list();
        StringBuilder sb = new StringBuilder();
        for (StudentDTO record : students) {
            sb.append(record.toString()).append("\n");
            lastId = record.getId();
        }
        log.debug(sb.toString());

        log.debug("----Updating Record with ID = 2 -----");
        jdbcTemplateDemo.update(lastId, 20);

        log.debug("----Listing Record with ID = 2 -----");
        StudentDTO student = jdbcTemplateDemo.getById(lastId);
        log.debug(student.toString());
    }

}
