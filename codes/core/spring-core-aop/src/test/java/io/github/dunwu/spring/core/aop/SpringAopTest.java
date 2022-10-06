package io.github.dunwu.spring.core.aop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author Zhang Peng
 */
@ContextConfiguration(locations = "classpath:spring-aop.xml")
public class SpringAopTest {

    @Autowired
    Performer performer;

    @Test
    public void test() {
        Assertions.assertEquals("play a song", performer.perform());
    }

}
