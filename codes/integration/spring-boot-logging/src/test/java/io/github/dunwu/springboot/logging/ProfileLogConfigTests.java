package io.github.dunwu.springboot.logging;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@ExtendWith(value = { OutputCaptureExtension.class })
public class ProfileLogConfigTests {

    @Test
    public void testProfileTest(CapturedOutput output) {
        LoggingApplication.main(new String[] { "--spring.profiles.active=test" });
        Assertions.assertThat(output.getOut()).contains(
            "Print Error Message",
            "Print Warn Message",
            "Print Info Message",
            "Print Debug Message",
            "Print Trace Message"
        );
    }

}
