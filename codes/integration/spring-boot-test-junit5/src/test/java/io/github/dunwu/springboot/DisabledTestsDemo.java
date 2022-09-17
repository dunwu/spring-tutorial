package io.github.dunwu.springboot;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class DisabledTestsDemo {

    @Test
    void testWillBeExecuted() {
    }

    @Disabled
    @Test
    void testWillBeSkipped() {
    }

}
