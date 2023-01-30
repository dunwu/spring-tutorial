package example.spring.core.aop;

import example.spring.core.aop.entity.Performer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Zhang Peng
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:spring-aop.xml")
public class SpringAopTest {

    @Autowired
    private Performer performer;

    @Test
    public void test() {
        Assertions.assertEquals("play a song", performer.perform());
    }

}
