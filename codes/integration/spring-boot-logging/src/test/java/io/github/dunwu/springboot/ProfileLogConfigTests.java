package io.github.dunwu.springboot;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.rule.OutputCapture;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

public class ProfileLogConfigTests {

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Test
    public void testProfileTest() {
        SpringBootLoggingApplication.main(new String[] { "--spring.profiles.active=test" });
        this.outputCapture.expect(containsString("Print Error Message"));
        this.outputCapture.expect(containsString("Print Warn Message"));
        this.outputCapture.expect(containsString("Print Info Message"));
        this.outputCapture.expect(containsString("Print Debug Message"));
        this.outputCapture.expect(not(containsString("Print Trace Message")));
    }

}
