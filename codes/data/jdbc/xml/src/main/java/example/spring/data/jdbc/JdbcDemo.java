package example.spring.data.jdbc;

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

    public static void execJdbcOper(UserDao userDao) {
        if (userDao != null) {
            log.info("Connect to datasource success.");
        } else {
            log.error("Connect to datasource failed!");
            return;
        }

        List<User> list = userDao.list();
        list.forEach(item -> log.info(item.toString()));
    }

    public static void main(String[] args) {
        ApplicationContext mysqlApplicationContext = getMysqlApplicationContext();
        UserDao mysqlUserDao = mysqlApplicationContext.getBean(UserDao.class);
        execJdbcOper(mysqlUserDao);

        ApplicationContext h2ApplicationContext = getH2ApplicationContext();
        UserDao h2UserDao = h2ApplicationContext.getBean(UserDao.class);
        execJdbcOper(h2UserDao);
    }

}
