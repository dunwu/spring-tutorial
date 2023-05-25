package example.trace.opentelemetry;

import cn.hutool.core.util.ReflectUtil;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.context.propagation.TextMapGetter;
import io.opentelemetry.context.propagation.TextMapPropagator;
import io.opentelemetry.exporter.logging.LoggingSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import io.opentelemetry.semconv.resource.attributes.ResourceAttributes;
import io.opentelemetry.semconv.trace.attributes.SemanticAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * OpenTelemetry 支持类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2023-05-24
 */
@Component
public class LoggingOpenTelemetrySupport {

    private Tracer tracer;
    private OpenTelemetry openTelemetry;

    private Span currentSpan;

    @PostConstruct
    public void init() {
        openTelemetry = initOpenTelemetry();
        tracer = openTelemetry.getTracer("server", "1.0.0");
    }

    public Tracer getTracer() {
        return tracer;
    }

    public OpenTelemetry getOpenTelemetry() {
        return openTelemetry;
    }

    private OpenTelemetry initOpenTelemetry() {
        Resource resource =
            Resource.getDefault().merge(Resource.create(Attributes.of(ResourceAttributes.SERVICE_NAME, "server")));

        SdkTracerProvider sdkTracerProvider = SdkTracerProvider.builder()
                                                               .addSpanProcessor(SimpleSpanProcessor.create(
                                                                   LoggingSpanExporter.create()))
                                                               .setResource(resource)
                                                               .build();

        return OpenTelemetrySdk.builder()
                               .setTracerProvider(sdkTracerProvider)
                               .setPropagators(ContextPropagators.create(W3CTraceContextPropagator.getInstance()))
                               .buildAndRegisterGlobal();
    }

    public Span startSpanFromPropagator(HttpRequest httpRequest) {

        TextMapPropagator textMapPropagator = openTelemetry.getPropagators().getTextMapPropagator();

        Context context =
            textMapPropagator.extract(Context.current(), httpRequest.getHeaders(), new TextMapGetter<HttpHeaders>() {
                @Override
                public Iterable<String> keys(HttpHeaders headers) {
                    for (String key : headers.keySet()) {
                        System.out.println("[headers key]key: " + key + ", value" + headers.get(key));
                    }
                    return headers.keySet();
                }

                @Override
                public String get(HttpHeaders headers, String s) {
                    if (headers.containsKey(s)) {
                        return headers.get(s).get(0);
                    }
                    return null;
                }
            });

        currentSpan = tracer.spanBuilder(httpRequest.getURI().toString())
                            .setParent(context)
                            .setSpanKind(SpanKind.SERVER)
                            .setAttribute(SemanticAttributes.HTTP_METHOD, httpRequest.getMethod().toString())
                            .setAttribute(SemanticAttributes.HTTP_URL, httpRequest.getURI().toString())
                            .startSpan();
        return currentSpan;
    }

    public Span startSpanFromParent(String key) {
        return startSpanFromParent(key, getCurrentSpan());
    }

    public Span startSpanFromParent(String key, Span parentSpan) {
        currentSpan = tracer.spanBuilder(key)
                            .setParent(Context.current().with(parentSpan))
                            .startSpan();
        return currentSpan;
    }

    public Span getCurrentSpan() {
        return currentSpan;
    }

    public SpanContext getCurrentSpanContext() {
        if (currentSpan == null) {
            return null;
        }
        return currentSpan.getSpanContext();
    }

    public SpanContext getParentSpanContext() {
        if (currentSpan == null) {
            return null;
        }
        return (SpanContext) ReflectUtil.getFieldValue(currentSpan, "parentSpanContext");
    }

    public SpanContext getParentSpanContext(Span span) {
        if (span == null) {
            return null;
        }
        return (SpanContext) ReflectUtil.getFieldValue(span, "parentSpanContext");
    }

}
