package io.github.dunwu.springboot;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Zhang Peng
 * @since 2018-12-22
 */
public class ParameterizedTestDemo {

    @ParameterizedTest
    @ValueSource(strings = { "123456789", "Hello World" })
    void palindromes(String candidate) {
        assertTrue(isMoreThan7(candidate));
    }

    private boolean isMoreThan7(String str) {
        if (str == null || str.length() <= 0) {
            return false;
        }

        return str.length() > 7;
    }

}
