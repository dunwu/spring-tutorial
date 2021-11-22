package io.github.dunwu.spring.ioc;

import io.github.dunwu.spring.core.bean.life.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/META-INF/spring-beans.xml")
public class SpringBeanTest {

    @Autowired
    private Person person;

    @Test
    public void test01() throws Exception {
        System.out.println("Person = " + person);
    }

}
