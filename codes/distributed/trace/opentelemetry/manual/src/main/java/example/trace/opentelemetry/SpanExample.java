package example.trace.opentelemetry;

import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;

/**
 * OpenTelemetry Span 手动埋点代码示例
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2023-05-24
 */
public class SpanExample {

    public static void main(String[] args) {
        singleSpanDemo();
        nestedSpanDemo();
        getSpanDemo();
        spanAttributesDemo();
        spanEventDemo();
        spanStatusAndExceptionDemo();
    }

    /**
     * 单 span 示例
     */
    public static void singleSpanDemo() {
        System.out.println("[single span]");
        Tracer tracer = LoggingOpenTelemetrySupport.getTracer();
        Span span = tracer.spanBuilder("SingleSpan").startSpan();
        // Make the span the current span
        try (Scope ss = span.makeCurrent()) {
            // In this scope, the span is the current/active span
        } finally {
            span.end();
        }
    }

    /**
     * 嵌套 span 示例
     */
    private static void nestedSpanDemo() {
        System.out.println("[nested span]");
        parentOne();
        parentTwo();
    }

    private static void parentOne() {
        Tracer tracer = LoggingOpenTelemetrySupport.getTracer();
        Span parentSpan = tracer.spanBuilder("ParentSpan").startSpan();
        try {
            childOne(parentSpan);
        } finally {
            parentSpan.end();
        }
    }

    private static void childOne(Span parentSpan) {
        // The OpenTelemetry API offers also an automated way to propagate the parent span on the current thread
        Tracer tracer = LoggingOpenTelemetrySupport.getTracer();
        Span childSpan = tracer.spanBuilder("ChildSpan")
                               .setParent(Context.current().with(parentSpan))
                               .startSpan();
        try {
            // do stuff
        } finally {
            childSpan.end();
        }
    }

    private static void parentTwo() {
        Tracer tracer = LoggingOpenTelemetrySupport.getTracer();
        Span parentSpan = tracer.spanBuilder("ParentSpan").startSpan();
        try (Scope scope = parentSpan.makeCurrent()) {
            childTwo();
        } finally {
            parentSpan.end();
        }
    }

    private static void childTwo() {
        Tracer tracer = LoggingOpenTelemetrySupport.getTracer();
        Span childSpan = tracer.spanBuilder("ChildSpan")
                               // NOTE: setParent(...) is not required;
                               // `Span.current()` is automatically added as the parent
                               .startSpan();
        try (Scope scope = childSpan.makeCurrent()) {
            // do stuff
        } finally {
            childSpan.end();
        }
    }

    /**
     * 获取 span 示例
     */
    private static void getSpanDemo() {
        System.out.println("[get span]");
        Tracer tracer = LoggingOpenTelemetrySupport.getTracer();
        Span span = tracer.spanBuilder("SingleSpan").startSpan();
        // Make the span the current span
        try (Scope ss = span.makeCurrent()) {
            // In this scope, the span is the current/active span
            Span currentSpan = Span.current();
            System.out.println("span: " + span.getSpanContext().getSpanId());
            System.out.println("Span.current(): " + currentSpan.getSpanContext().getSpanId());
            if (span.equals(currentSpan)) {
                System.out.println("span is same");
            }
        } finally {
            span.end();
        }
    }

    private static void spanAttributesDemo() {
        System.out.println("[span attributes]");
        Tracer tracer = LoggingOpenTelemetrySupport.getTracer();
        Span span = tracer.spanBuilder("/hello").setSpanKind(SpanKind.CLIENT).startSpan();
        span.setAttribute("http.method", "GET");
        span.setAttribute("http.url", "/hello");
        try (Scope ss = span.makeCurrent()) {
            // In this scope, the span is the current/active span
            System.out.println("span: " + Span.current().getSpanContext());
        } finally {
            span.end();
        }
    }

    private static void spanEventDemo() {
        System.out.println("[span event]");
        Tracer tracer = LoggingOpenTelemetrySupport.getTracer();
        Span span = tracer.spanBuilder("EventSpan").setSpanKind(SpanKind.CLIENT).startSpan();
        Attributes eventAttributes = Attributes.of(
            AttributeKey.stringKey("key"), "value",
            AttributeKey.longKey("result"), 0L);

        span.addEvent("End Computation", eventAttributes);
        try (Scope ss = span.makeCurrent()) {
            // In this scope, the span is the current/active span
            Span child = tracer.spanBuilder("childWithLink")
                               .addLink(span.getSpanContext())
                               .startSpan();
            try (Scope scope = child.makeCurrent()) {
                System.out.println("span: " + child.getSpanContext());
            } finally {
                child.end();
            }
        } finally {
            span.end();
        }
    }

    private static void spanStatusAndExceptionDemo() {
        System.out.println("[span status]");
        Tracer tracer = LoggingOpenTelemetrySupport.getTracer();
        Span span = tracer.spanBuilder("StatusSpan").startSpan();
        // put the span into the current Context
        try (Scope scope = span.makeCurrent()) {
            // do something
            throw new RuntimeException("force exception");
        } catch (Throwable t) {
            span.setStatus(StatusCode.ERROR, "Something bad happened!");
            span.recordException(t);
        } finally {
            span.end(); // Cannot set a span after this call
        }
    }

}
