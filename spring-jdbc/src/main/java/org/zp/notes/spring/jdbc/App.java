package org.zp.notes.spring.jdbc;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/spring/spring-servlet.xml");

        JdbcTemplateDemo jdbcTemplateDemo = (JdbcTemplateDemo) ctx.getBean("jdbcTemplateDemo");

        System.out.println("------Records Creation--------");
        jdbcTemplateDemo.create("Zara", 11);
        jdbcTemplateDemo.create("Nuha", 2);
        jdbcTemplateDemo.create("Ayan", 15);

        System.out.println("------Listing Multiple Records--------");
        List<StudentDTO> students = jdbcTemplateDemo.list();
        for (StudentDTO record : students) {
            System.out.print("ID : " + record.getId());
            System.out.print(", Name : " + record.getName());
            System.out.println(", Age : " + record.getAge());
        }

        System.out.println("----Updating Record with ID = 2 -----");
        jdbcTemplateDemo.update(2, 20);

        System.out.println("----Listing Record with ID = 2 -----");
        StudentDTO student = jdbcTemplateDemo.getById(2);
        System.out.print("ID : " + student.getId());
        System.out.print(", Name : " + student.getName());
        System.out.println(", Age : " + student.getAge());

        // 关闭应用上下文容器，不要忘记这句话
        ((ClassPathXmlApplicationContext) ctx).close();
    }
}
