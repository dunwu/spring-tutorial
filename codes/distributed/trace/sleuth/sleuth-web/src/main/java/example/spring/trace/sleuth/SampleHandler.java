package example.spring.trace.sleuth;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author Spencer Gibb
 */
@Component
public class SampleHandler {

    private static final Log log = LogFactory.getLog(SampleHandler.class);

    private final Tracer tracer;

    private final Random random = new Random();

    public SampleHandler(Tracer tracer) {
        this.tracer = tracer;
    }

    @Async
    public void doAsyncTask() throws InterruptedException {
        log.info("[method] call doAsyncTask");
        int millis = this.random.nextInt(1000);
        Thread.sleep(millis);
        this.tracer.currentSpan().tag("async-task-sleep-millis", String.valueOf(millis));
    }

}
