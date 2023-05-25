package example.spring.trace.opentelemetry;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class DemoController implements ApplicationListener<ServletWebServerInitializedEvent> {

    private static final Log log = LogFactory.getLog(DemoController.class);

    private int port;

    @Autowired
    private DemoService userService;

    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    private void biz() {

        Tracer tracer = OpenTelemetrySupport.getTracer();
        Span span = tracer.spanBuilder("biz (manual)")
                          // 可选，自动设置
                          .setParent(Context.current().with(Span.current()))
                          .startSpan();

        try (Scope scope = span.makeCurrent()) {
            span.setAttribute("bizId", "111");

            executorService.submit(() -> {
                Span asyncSpan = tracer.spanBuilder("async")
                                       .setParent(Context.current().with(span))
                                       .startSpan();
                try {
                    // some async jobs
                    Thread.sleep(1000L);
                } catch (Throwable e) {
                    e.printStackTrace();
                } finally {
                    asyncSpan.end();
                }
            });

            // fake biz logic
            Thread.sleep(1000);
            System.out.println("biz done");
            OpenTelemetry openTelemetry = GlobalOpenTelemetry.get();
            openTelemetry.getPropagators();
        } catch (Throwable t) {
            span.setStatus(StatusCode.ERROR, "handle biz error");
        } finally {
            span.end();
        }
    }

    private void child(String userType) {
        Span span = OpenTelemetrySupport.getTracer().spanBuilder("ChildSpan").startSpan();
        try (Scope scope = span.makeCurrent()) {
            span.setAttribute("user.type", userType);
            biz();
        } catch (Throwable t) {
            span.setStatus(StatusCode.ERROR, "handle child span error");
        } finally {
            span.end();
        }
    }

    @RequestMapping("/async")
    public String async() {
        System.out.println("UserController.async -- " + Thread.currentThread().getId());

        Span span = OpenTelemetrySupport.getTracer().spanBuilder("ParentSpan").startSpan();
        span.setAttribute("user.id", "123456");
        try (Scope scope = span.makeCurrent()) {
            userService.async();
            child("vip");
        } catch (Throwable t) {
            span.setStatus(StatusCode.ERROR, "handle parent span error");
        } finally {
            span.end();
        }
        return "async";
    }

    @Override
    public void onApplicationEvent(ServletWebServerInitializedEvent event) {
        this.port = event.getSource().getPort();
    }

}
