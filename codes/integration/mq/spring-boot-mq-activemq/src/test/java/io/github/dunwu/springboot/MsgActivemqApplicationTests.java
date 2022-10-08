package io.github.dunwu.springboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@ExtendWith(value = { OutputCaptureExtension.class })
@SpringBootTest
public class MsgActivemqApplicationTests {

    @Autowired
    private Producer producer;

    @Test
    public void sendSimpleMessage(CapturedOutput output) throws InterruptedException {
        this.producer.send("Hello World");
        Thread.sleep(1000L);
        Assertions.assertThat(output.getOut().contains("Hello World")).isTrue();
    }

}
