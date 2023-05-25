package example.spring.trace.opentelemetry;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.semconv.resource.attributes.ResourceAttributes;

/**
 * OpenTelemetry 支持类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2023-05-24
 */
public class OpenTelemetrySupport {

    private static final Tracer tracer;
    private static final OpenTelemetry openTelemetry;

    public static final String SERVICE_NAME = "opentelemetry-test";

    static {
        Resource resource = Resource.getDefault()
                                    .merge(Resource.create(Attributes.of(
                                        ResourceAttributes.SERVICE_NAME, SERVICE_NAME
                                        // ResourceAttributes.HOST_NAME, "<host-name>"
                                    )));

        SdkTracerProvider sdkTracerProvider =
            SdkTracerProvider.builder()
                             .addSpanProcessor(BatchSpanProcessor.builder(OtlpGrpcSpanExporter.builder().build()).build())
                             .setResource(resource)
                             .build();

        openTelemetry = OpenTelemetrySdk.builder()
                                        .setTracerProvider(sdkTracerProvider)
                                        .setPropagators(ContextPropagators.create(W3CTraceContextPropagator.getInstance()))
                                        .buildAndRegisterGlobal();

        tracer = openTelemetry.getTracer("my-trace", "1.0.0");
    }

    public static Tracer getTracer() {
        return tracer;
    }

    public static OpenTelemetry getOpenTelemetry() {
        return openTelemetry;
    }

}
