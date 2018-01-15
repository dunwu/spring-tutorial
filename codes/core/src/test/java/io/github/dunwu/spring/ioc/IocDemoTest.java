package io.github.dunwu.spring.ioc;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.github.dunwu.spring.ioc.annotation.Musician;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/spring-annotation.xml")
public class IocDemoTest {
    @Autowired
    Musician musician;

    @Test
    public void test01() throws Exception {
        musician.setName("艺术家");
        musician.setSong("夜曲");
        Assert.assertEquals("艺术家演绎夜曲", musician.perform());
    }
}
