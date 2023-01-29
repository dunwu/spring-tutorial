package example.spring.core.bean;

import example.spring.core.bean.entity.person.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DisplayName("测试 BeanFactoryPostProcessor")
@ExtendWith(SpringExtension.class)
@ContextConfiguration("/META-INF/bean/BeanFactoryPostProcessor.xml")
public class BeanFactoryPostProcessorTests {

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
