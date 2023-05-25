package example.trace.opentelemetry;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.context.Scope;
import org.springframework.http.HttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TracingFilter implements Filter {

    private final LoggingOpenTelemetrySupport loggingOpenTelemetrySupport;

    public TracingFilter(LoggingOpenTelemetrySupport loggingOpenTelemetrySupport) {
        this.loggingOpenTelemetrySupport = loggingOpenTelemetrySupport;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws
        IOException, ServletException {

        HttpRequest httpRequest = new ServletServerHttpRequest((HttpServletRequest) servletRequest);

        Span span = loggingOpenTelemetrySupport.startSpanFromPropagator(httpRequest);
        SpanContext currentSpanContext = loggingOpenTelemetrySupport.getCurrentSpanContext();
        SpanContext parentSpanContext = loggingOpenTelemetrySupport.getParentSpanContext(span);
        if (parentSpanContext != null) {
            System.out.println("server parent traceId: " + parentSpanContext.getTraceId());
            System.out.println("server parent spanId: " + parentSpanContext.getSpanId());
        }

        try (Scope scope = span.makeCurrent()) {
            System.out.println("server current traceId: " + currentSpanContext.getTraceId());
            System.out.println("server current spanId: " + currentSpanContext.getSpanId());
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            span.setStatus(StatusCode.ERROR, "HTTP Code: " + ((HttpServletResponse) servletResponse).getStatus());
            span.recordException(e);
            throw e;
        } finally {
            span.end();
        }
    }

}
