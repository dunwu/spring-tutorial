package io.github.dunwu.spring.core.aop;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Zhang Peng
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-aop.xml")
public class SpringAopTest {

    @Autowired
    Performer performer;

    @Test
    public void test() {
        Assert.assertEquals("play a song", performer.perform());
    }
}
