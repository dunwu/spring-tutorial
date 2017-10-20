package io.github.dunwu.spring.data.jdbc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JdbcDemo {
    private static final Logger log = LoggerFactory.getLogger(JdbcDemo.class);

    public static ApplicationContext getH2ApplicationContext() {
        return new ClassPathXmlApplicationContext("/db/spring-h2.xml");
    }

    public static ApplicationContext getMysqlApplicationContext() {
        return new ClassPathXmlApplicationContext("/db/spring-mysql.xml");
    }

    public static ApplicationContext getDruidApplicationContext() {
        return new ClassPathXmlApplicationContext("/db/spring-druid.xml");
    }

    public static void execJdbcOper(MyJdbcTemplateImpl jdbcTemplateDemo) {
        log.debug("------Records Creation--------");
        jdbcTemplateDemo.create("Zara", 11);
        jdbcTemplateDemo.create("Nuha", 2);
        jdbcTemplateDemo.create("Ayan", 15);

        log.debug("------Listing Multiple Records--------");
        int lastId = 0;
        List<StudentDTO> students = jdbcTemplateDemo.list();
        for (StudentDTO record : students) {
            System.out.print("ID : " + record.getId());
            System.out.println(", Name : " + record.getName());
            log.debug(", Age : " + record.getAge());
            lastId = record.getId();
        }

        log.debug("----Updating Record with ID = 2 -----");
        jdbcTemplateDemo.update(lastId, 20);

        log.debug("----Listing Record with ID = 2 -----");
        StudentDTO student = jdbcTemplateDemo.getById(lastId);
        System.out.print("ID : " + student.getId());
        System.out.println(", Name : " + student.getName());
        log.debug(", Age : " + student.getAge());
    }
}
