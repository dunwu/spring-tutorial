package me.chongfeng.spring.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class JdbcDemo {
    private static final Logger log = LoggerFactory.getLogger(JdbcDemo.class);

    public static void main(String[] args) {
        /*ApplicationContext ctx = getH2ApplicationContext();
        ApplicationContext ctx = getMysqlApplicationContext();*/
        ApplicationContext ctx = getDruidApplicationContext();

        MyJdbcTemplate jdbcTemplateDemo = (MyJdbcTemplate) ctx.getBean("myJdbcTemplate");

        execJdbcOper(jdbcTemplateDemo);

        // 关闭应用上下文容器，不要忘记这句话
        ((ClassPathXmlApplicationContext) ctx).close();
    }

    public static ApplicationContext getH2ApplicationContext() {
        log.debug("=============== mysql jdbc test ===============");
        return new ClassPathXmlApplicationContext("/db/spring-h2.xml");
    }

    public static ApplicationContext getMysqlApplicationContext() {
        log.debug("=============== mysql jdbc test ===============");
        return new ClassPathXmlApplicationContext("/db/spring-mysql.xml");
    }

    public static ApplicationContext getDruidApplicationContext() {
        log.debug("=============== druid mysql  jdbc test ===============");
        return new ClassPathXmlApplicationContext("/db/spring-druid.xml");
    }

    public static void execJdbcOper(MyJdbcTemplate jdbcTemplateDemo) {
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
