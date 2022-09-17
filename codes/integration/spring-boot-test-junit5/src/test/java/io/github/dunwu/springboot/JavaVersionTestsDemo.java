package io.github.dunwu.springboot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnJre;

import static org.junit.jupiter.api.condition.JRE.*;

/**
 * @author Zhang Peng
 * @since 2018-12-22
 */
public class JavaVersionTestsDemo {

    @Test
    @DisabledOnJre(JAVA_9)
    void notOnJava9() {
        System.out.println("NOT ON JAVA9 TEST");
    }

    @Test
    @EnabledOnJre({ JAVA_9, JAVA_10 })
    void onJava9Or10() {
        System.out.println("JAVA9, JAVA10 TEST");
    }

    @Test
    @EnabledOnJre(JAVA_8)
    void onlyOnJava8() {
        System.out.println("JAVA8 TEST");
    }

}
