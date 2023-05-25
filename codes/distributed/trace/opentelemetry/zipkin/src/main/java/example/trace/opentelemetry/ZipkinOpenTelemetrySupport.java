package example.trace.opentelemetry;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.exporter.zipkin.ZipkinSpanExporter;
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
public class ZipkinOpenTelemetrySupport {

    public static final String SERVICE_NAME = "opentelemetry-test";
    private static final OpenTelemetry openTelemetry;

    static {
        openTelemetry = initOpenTelemetry("localhost", 9411);
    }

    public static OpenTelemetry getOpenTelemetry() {
        return openTelemetry;
    }

    private static OpenTelemetry initOpenTelemetry(String ip, int port) {
        Resource resource = Resource.getDefault()
                                    .merge(Resource.create(Attributes.of(
                                        ResourceAttributes.SERVICE_NAME, SERVICE_NAME
                                        // ResourceAttributes.HOST_NAME, "<host-name>"
                                    )));

        String endpoint = String.format("http://%s:%s/api/v2/spans", ip, port);
        ZipkinSpanExporter zipkinExporter = ZipkinSpanExporter.builder().setEndpoint(endpoint).build();
        SdkTracerProvider tracerProvider =
            SdkTracerProvider.builder()
                             .addSpanProcessor(SimpleSpanProcessor.create(zipkinExporter))
                             .setResource(resource)
                             .build();

        // add a shutdown hook to shut down the SDK
        Runtime.getRuntime().addShutdownHook(new Thread(tracerProvider::close));

        return OpenTelemetrySdk.builder()
                               .setTracerProvider(tracerProvider)
                               .setPropagators(ContextPropagators.create(W3CTraceContextPropagator.getInstance()))
                               .buildAndRegisterGlobal();
    }

}
