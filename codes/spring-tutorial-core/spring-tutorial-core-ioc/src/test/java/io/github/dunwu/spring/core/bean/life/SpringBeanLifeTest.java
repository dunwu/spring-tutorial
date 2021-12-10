package io.github.dunwu.spring.core.bean.life;

import io.github.dunwu.spring.core.bean.entity.person.Person;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/META-INF/bean/life/BeanFactoryPostProcessor.xml")
public class SpringBeanLifeTest {

    @Autowired
    private Person person;

    @Test
    public void test() {
        System.out.println("Person = " + person);
        Assertions.assertEquals("广州", person.getAddress());
        Assertions.assertEquals("张三", person.getName());
        Assertions.assertEquals("110", person.getPhone());
    }

}
