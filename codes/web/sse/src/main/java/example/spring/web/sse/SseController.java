package example.spring.web.sse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2024-04-16
 */
@CrossOrigin
@RestController
@RequestMapping("/sse")
public class SseController {

    public static final String PREFIX = "user:";
    public static final String[] WORDS = "The quick brown fox jumps over the lazy dog.".split(" ");

    @GetMapping(value = "/connect/{userId}", produces = "text/event-stream;charset=UTF-8")
    public SseEmitter connect(@PathVariable String userId) {
        return SseUtil.connect(PREFIX + userId);
    }

    @GetMapping("/close/{userId}")
    public boolean close(@PathVariable String userId) {
        return SseUtil.close(PREFIX + userId);
    }

    @GetMapping("/send/{userId}")
    public boolean send(@PathVariable String userId, @RequestParam("msg") String msg) {
        SseUtil.send(PREFIX + userId, "收到消息：" + msg);
        return true;
    }

}
