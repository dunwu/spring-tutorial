package example.spring.trace.opentelemetry;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    @Async
    public void async() {
        System.out.println("DemoService.async begin. thread: " + Thread.currentThread().getId());
        System.out.println("DemoService.async end. ");
    }

}
