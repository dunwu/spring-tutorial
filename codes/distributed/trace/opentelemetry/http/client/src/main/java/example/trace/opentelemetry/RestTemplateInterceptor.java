package example.trace.opentelemetry;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import io.opentelemetry.context.propagation.TextMapPropagator;
import io.opentelemetry.semconv.trace.attributes.SemanticAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws
        IOException {

        Tracer tracer = LoggingOpenTelemetrySupport.getTracer();
        OpenTelemetry openTelemetry = LoggingOpenTelemetrySupport.getOpenTelemetry();
        TextMapPropagator textMapPropagator = openTelemetry.getPropagators().getTextMapPropagator();

        Span span = tracer.spanBuilder("/client/hello").setSpanKind(SpanKind.CLIENT).startSpan();
        try (Scope scope = span.makeCurrent()) {
            // Use the Semantic Conventions.
            // (Note that to set these, Span does not *need* to be the current instance in Context or Scope.)
            span.setAttribute(SemanticAttributes.HTTP_METHOD, "GET");
            span.setAttribute(SemanticAttributes.HTTP_URL, request.getURI().toString());

            System.out.println("client traceId: " + span.getSpanContext().getTraceId());
            System.out.println("client spanId: " + span.getSpanContext().getSpanId());

            HttpHeaders headers = request.getHeaders();
            headers.add("traceId", span.getSpanContext().getTraceId());
            headers.add("spanId", span.getSpanContext().getSpanId());

            textMapPropagator.inject(Context.current(), headers, this::set);
            return execution.execute(request, body);
        } finally {
            span.end();
        }
    }

    private void set(HttpHeaders headers, String key, String value) {
        headers.set(key, value);
    }

}
