package example.spring.trace.sleuth.rmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/rmq")
public class SampleController {

    @Autowired
    private MySource mySource;

    @GetMapping("/send")
    public boolean send() {
        // 创建 Message
        SampleMessage message = new SampleMessage().setId(new Random().nextInt());
        // 创建 Spring Message 对象
        Message<SampleMessage> springMessage = MessageBuilder.withPayload(message).build();
        // 发送消息
        return mySource.rmqOutput1().send(springMessage);
    }

}
