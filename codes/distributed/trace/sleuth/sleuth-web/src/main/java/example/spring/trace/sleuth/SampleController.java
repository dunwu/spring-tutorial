package example.spring.trace.sleuth;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.ApplicationListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * @author Spencer Gibb
 */
@RestController
public class SampleController implements ApplicationListener<ServletWebServerInitializedEvent> {

    private static final Log log = LogFactory.getLog(SampleController.class);

    private final Tracer tracer;
    private final RestTemplate restTemplate;
    private final SampleHandler handler;

    private final Random random = new Random();
    private int port;

    public SampleController(Tracer tracer, RestTemplate restTemplate, SampleHandler handler) {
        this.tracer = tracer;
        this.restTemplate = restTemplate;
        this.handler = handler;
    }

    @RequestMapping("/")
    public String index() throws InterruptedException {
        log.info("[http] call /");
        Thread.sleep(this.random.nextInt(1000));
        Map<String, String> params = new HashMap<>(1);
        params.put("name", "world");
        return this.restTemplate.getForObject("http://localhost:" + this.port + "/hello?name={name}",
            String.class, params);
    }

    @RequestMapping("/hello")
    public String hello(@RequestParam("name") String name) throws InterruptedException {
        log.info("[http] call /hello");
        int millis = this.random.nextInt(1000);
        Thread.sleep(millis);
        this.tracer.currentSpan().tag("random-sleep-millis", String.valueOf(millis));
        return "hello, " + name;
    }

    @RequestMapping("/callable")
    public Callable<String> callable() {
        return () -> {
            log.info("[http] call /callable");
            int millis = SampleController.this.random.nextInt(1000);
            Thread.sleep(millis);
            SampleController.this.tracer.currentSpan().tag("callable-sleep-millis", String.valueOf(millis));
            Span span = SampleController.this.tracer.currentSpan();
            return "async hello: " + span;
        };
    }

    @RequestMapping("/async")
    public String async() throws InterruptedException {
        log.info("[http] call /async");
        this.handler.doAsyncTask();
        return "async done";
    }

    @RequestMapping("/traced")
    public String traced() throws InterruptedException {
        log.info("[http] call /traced");
        Span span = this.tracer.nextSpan().name("http:customTraceEndpoint");
        int millis = this.random.nextInt(1000);
        log.info(String.format("Sleeping for [%d] millis", millis));
        Thread.sleep(millis);
        this.tracer.currentSpan().tag("random-sleep-millis", String.valueOf(millis));
        String result = this.restTemplate.getForObject("http://localhost:" + this.port + "/callable", String.class);
        span.end();
        return "traced/" + result;
    }

    @RequestMapping("/start")
    public String start() throws InterruptedException {
        log.info("[http] call /start");
        int millis = this.random.nextInt(1000);
        log.info(String.format("Sleeping for [%d] millis", millis));
        Thread.sleep(millis);
        this.tracer.currentSpan().tag("random-sleep-millis", String.valueOf(millis));
        String s = this.restTemplate.getForObject("http://localhost:" + this.port + "/callable", String.class);
        return "start/" + s;
    }

    @Override
    public void onApplicationEvent(ServletWebServerInitializedEvent event) {
        this.port = event.getSource().getPort();
    }

}
