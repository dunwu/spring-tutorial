package org.zp.notes.db.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        ApplicationContext ctx = getDruidApplicationContext();

        JdbcTemplateDemo jdbcTemplateDemo = (JdbcTemplateDemo) ctx.getBean("jdbcTemplateDemo");

        execJdbcOper(jdbcTemplateDemo);

        // 关闭应用上下文容器，不要忘记这句话
        ((ClassPathXmlApplicationContext) ctx).close();
    }

    public static ApplicationContext getMysqlApplicationContext() {
        log.debug("=============== mysql jdbc test ===============");
        return new ClassPathXmlApplicationContext("/spring/spring-servlet.xml");
    }

    public static ApplicationContext getDruidApplicationContext() {
        log.debug("=============== druid mysql  jdbc test ===============");
        return new ClassPathXmlApplicationContext("/spring/spring-druid.xml");
    }

    public static void execJdbcOper(JdbcTemplateDemo jdbcTemplateDemo) {
        log.debug("------Records Creation--------");
        jdbcTemplateDemo.create("Zara", 11);
        jdbcTemplateDemo.create("Nuha", 2);
        jdbcTemplateDemo.create("Ayan", 15);

        log.debug("------Listing Multiple Records--------");
        int lastId = 0;
        List<StudentDTO> students = jdbcTemplateDemo.list();
        for (StudentDTO record : students) {
            System.out.print("ID : " + record.getId());
            System.out.print(", Name : " + record.getName());
            log.debug(", Age : " + record.getAge());
            lastId = record.getId();
        }

        log.debug("----Updating Record with ID = 2 -----");
        jdbcTemplateDemo.update(lastId, 20);

        log.debug("----Listing Record with ID = 2 -----");
        StudentDTO student = jdbcTemplateDemo.getById(lastId);
        System.out.print("ID : " + student.getId());
        System.out.print(", Name : " + student.getName());
        log.debug(", Age : " + student.getAge());
    }
}
