package example.spring.trace.sleuth;

import example.spring.trace.sleuth.entity.Greeting;
import example.spring.trace.sleuth.entity.HelloMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {

    private static final Log log = LogFactory.getLog(GreetingController.class);

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        log.info("Hello: " + message);
        // simulated delay
        Thread.sleep(3000);
        // Then send back greeting
        return new Greeting("Hello, " + message.getName() + "!");
    }

}



