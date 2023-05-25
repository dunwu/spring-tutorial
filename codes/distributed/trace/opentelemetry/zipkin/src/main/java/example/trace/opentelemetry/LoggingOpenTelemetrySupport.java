package example.trace.opentelemetry;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.exporter.logging.LoggingSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import io.opentelemetry.semconv.resource.attributes.ResourceAttributes;

/**
 * OpenTelemetry 支持类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2023-05-24
 */
public class LoggingOpenTelemetrySupport {

    public static final String SERVICE_NAME = "opentelemetry-test";
    private static final OpenTelemetry openTelemetry;

    static {
        openTelemetry = initOpenTelemetry();
    }

    public static OpenTelemetry getOpenTelemetry() {
        return openTelemetry;
    }

    private static OpenTelemetry initOpenTelemetry() {
        Resource resource = Resource.getDefault()
                                    .merge(Resource.create(Attributes.of(
                                        ResourceAttributes.SERVICE_NAME, SERVICE_NAME
                                    )));

        SdkTracerProvider sdkTracerProvider =
            SdkTracerProvider.builder()
                             .addSpanProcessor(SimpleSpanProcessor.create(LoggingSpanExporter.create()))
                             .setResource(resource)
                             .build();

        return OpenTelemetrySdk.builder()
                               .setTracerProvider(sdkTracerProvider)
                               .setPropagators(ContextPropagators.create(W3CTraceContextPropagator.getInstance()))
                               .buildAndRegisterGlobal();
    }

}
