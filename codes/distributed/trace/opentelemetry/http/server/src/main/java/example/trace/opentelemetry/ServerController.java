package example.trace.opentelemetry;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.context.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server")
public class ServerController {

    @Autowired
    private LoggingOpenTelemetrySupport loggingOpenTelemetrySupport;

    @GetMapping("/hello")
    public String hello(@RequestParam("name") String name) {
        Span childSpan = loggingOpenTelemetrySupport.startSpanFromParent("ChildSpan");
        try (Scope scope = childSpan.makeCurrent()) {

            System.out.println("server childSpan traceId: " + childSpan.getSpanContext().getTraceId());
            System.out.println("server childSpan spanId: " + childSpan.getSpanContext().getSpanId());

            System.out.println("server Span.current() traceId: " + Span.current().getSpanContext().getTraceId());
            System.out.println("server Span.current() spanId: " + Span.current().getSpanContext().getSpanId());

            System.out.println("server loggingOpenTelemetrySupport.getCurrentSpan() traceId: " + loggingOpenTelemetrySupport.getCurrentSpan().getSpanContext().getTraceId());
            System.out.println("server loggingOpenTelemetrySupport.getCurrentSpan() spanId: " + loggingOpenTelemetrySupport.getCurrentSpan().getSpanContext().getSpanId());
            System.out.println("server loggingOpenTelemetrySupport.getCurrentSpan() parentSpanId: " + loggingOpenTelemetrySupport.getParentSpanContext().getSpanId());
            return "hello," + name;
        } finally {
            childSpan.end();
        }
    }

}
