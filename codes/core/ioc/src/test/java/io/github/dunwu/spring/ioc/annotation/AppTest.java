package io.github.dunwu.spring.ioc.annotation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/spring-annotation.xml")
public class AppTest {
    @Autowired
    Musician musician;

    @Test
    public void test01() throws Exception {
        musician.setName("艺术家");
        musician.setSong("夜曲");
        musician.perform();
    }
}
