package example.trace.opentelemetry;

import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.exporter.logging.LoggingSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.SpanLimits;
import io.opentelemetry.sdk.trace.data.LinkData;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import io.opentelemetry.sdk.trace.samplers.Sampler;
import io.opentelemetry.sdk.trace.samplers.SamplingDecision;
import io.opentelemetry.sdk.trace.samplers.SamplingResult;

import java.util.List;

/**
 * This example demonstrates various {@link SpanLimits} options and how to configure them into an SDK.
 */
class ConfigureTraceExample {

    public static void main(String[] args) {

        // 配置 Span 属性数量限制
        multiAttrSpanDemo();
        singleAttrSpanDemo();

        // OpenTelemetry offers three different default samplers（OpenTelemetry 提供三种默认采样方式）:
        //  - alwaysOn: it samples all traces（采用所有追踪数据）
        //  - alwaysOff: it rejects all traces（拒绝所有追踪数据）
        //  - probability: it samples traces based on the probability passed in input（按照指定采样率采样）
        sampleAlwaysOffDemo();
        sampleAlwaysOnDemo();
        sampleInRatio();
        sampleInCustom();
    }

    private static void multiAttrSpanDemo() {
        SdkTracerProvider provider =
            SdkTracerProvider.builder()
                             .addSpanProcessor(SimpleSpanProcessor.create(LoggingSpanExporter.create()))
                             .build();
        OpenTelemetrySdk openTelemetrySdk = OpenTelemetrySdk.builder().setTracerProvider(provider).build();
        printSpanLimits(openTelemetrySdk);

        // OpenTelemetry has a maximum of 128 Attributes by default for Spans, Links, and Events.
        Tracer tracer = openTelemetrySdk.getTracer("ConfigureTraceExample");
        Span multiAttrSpan = tracer.spanBuilder("Example Span Attributes").startSpan();
        multiAttrSpan.setAttribute("Attribute 1", "first attribute value");
        multiAttrSpan.setAttribute("Attribute 2", "second attribute value");
        multiAttrSpan.end();
    }

    private static void singleAttrSpanDemo() {

        // The configuration can be changed in the trace provider.
        // For example, we can change the maximum number of Attributes per span to 1.
        SpanLimits newConf = SpanLimits.builder().setMaxNumberOfAttributes(1).build();

        SdkTracerProvider provider =
            SdkTracerProvider.builder()
                             .addSpanProcessor(SimpleSpanProcessor.create(LoggingSpanExporter.create()))
                             .setSpanLimits(newConf)
                             .build();
        OpenTelemetrySdk openTelemetrySdk = OpenTelemetrySdk.builder().setTracerProvider(provider).build();
        printSpanLimits(openTelemetrySdk);

        // If more attributes than allowed by the configuration are set, they are dropped.
        Tracer tracer = openTelemetrySdk.getTracer("ConfigureTraceExample");
        Span singleAttrSpan = tracer.spanBuilder("Example Span Attributes").startSpan();
        singleAttrSpan.setAttribute("Attribute 1", "first attribute value");
        singleAttrSpan.setAttribute("Attribute 2", "second attribute value");
        singleAttrSpan.end();
    }

    private static void sampleAlwaysOnDemo() {

        // We build an SDK with the alwaysOn sampler.
        SdkTracerProvider provider =
            SdkTracerProvider.builder()
                             .addSpanProcessor(SimpleSpanProcessor.create(LoggingSpanExporter.create()))
                             .setSampler(Sampler.alwaysOn())
                             .build();
        OpenTelemetrySdk openTelemetrySdk = OpenTelemetrySdk.builder().setTracerProvider(provider).build();
        printSpanLimits(openTelemetrySdk);

        Tracer tracer = openTelemetrySdk.getTracer("ConfigureTraceExample");
        tracer.spanBuilder("Forwarded to all processors").startSpan().end();
        tracer.spanBuilder("Forwarded to all processors").startSpan().end();
    }

    private static void sampleAlwaysOffDemo() {
        // We build an SDK with the alwaysOff sampler.
        SdkTracerProvider provider =
            SdkTracerProvider.builder()
                             .addSpanProcessor(SimpleSpanProcessor.create(LoggingSpanExporter.create()))
                             .setSampler(Sampler.alwaysOff())
                             .build();
        OpenTelemetrySdk openTelemetrySdk = OpenTelemetrySdk.builder().setTracerProvider(provider).build();
        printSpanLimits(openTelemetrySdk);

        Tracer tracer = openTelemetrySdk.getTracer("ConfigureTraceExample");
        tracer.spanBuilder("Not forwarded to any processors").startSpan().end();
        tracer.spanBuilder("Not forwarded to any processors").startSpan().end();
    }

    private static void sampleInRatio() {
        // We build an SDK with the configuration to use the probability sampler which was configured to
        // sample
        // only 50% of the spans.
        Sampler traceIdRatioBased = Sampler.traceIdRatioBased(0.5);
        SdkTracerProvider provider =
            SdkTracerProvider.builder()
                             .addSpanProcessor(SimpleSpanProcessor.create(LoggingSpanExporter.create()))
                             .setSampler(traceIdRatioBased)
                             .build();
        OpenTelemetrySdk openTelemetrySdk = OpenTelemetrySdk.builder().setTracerProvider(provider).build();
        printSpanLimits(openTelemetrySdk);

        Tracer tracer = openTelemetrySdk.getTracer("ConfigureTraceExample");
        for (int i = 0; i < 10; i++) {
            tracer.spanBuilder(String.format("Span %d might be forwarded to all processors", i))
                  .startSpan()
                  .end();
        }
    }

    private static void sampleInCustom() {
        // Add MySampler to the Trace Configuration
        SdkTracerProvider provider =
            SdkTracerProvider.builder()
                             .addSpanProcessor(SimpleSpanProcessor.create(LoggingSpanExporter.create()))
                             .setSampler(new MySampler())
                             .build();
        OpenTelemetrySdk openTelemetrySdk = OpenTelemetrySdk.builder().setTracerProvider(provider).build();
        printSpanLimits(openTelemetrySdk);

        Tracer tracer = openTelemetrySdk.getTracer("ConfigureTraceExample");

        tracer.spanBuilder("#1 - SamPleD").startSpan().end();
        tracer.spanBuilder("#2 - SAMPLE this trace will be the first to be printed in the console output")
              .startSpan()
              .end();
        tracer.spanBuilder("#3 - Smth").startSpan().end();
        tracer.spanBuilder("#4 - SAMPLED this trace will be the second one shown in the console output")
              .startSpan()
              .end();
        tracer.spanBuilder("#5").startSpan().end();
    }

    private static void printSpanLimits(OpenTelemetrySdk sdk) {
        SpanLimits config = sdk.getSdkTracerProvider().getSpanLimits();
        System.err.println("==================================");
        System.err.print("Max number of attributes: ");
        System.err.println(config.getMaxNumberOfAttributes());
        System.err.print("Max number of attributes per event: ");
        System.err.println(config.getMaxNumberOfAttributesPerEvent());
        System.err.print("Max number of attributes per link: ");
        System.err.println(config.getMaxNumberOfAttributesPerLink());
        System.err.print("Max number of events: ");
        System.err.println(config.getMaxNumberOfEvents());
        System.err.print("Max number of links: ");
        System.err.println(config.getMaxNumberOfLinks());
        System.err.print("Sampler: ");
        System.err.println(sdk.getSdkTracerProvider().getSampler().getDescription());
    }

    /**
     * 实现自定义的采样器，即实现 {@link Sampler} 接口
     */
    static class MySampler implements Sampler {

        @Override
        public SamplingResult shouldSample(
            Context parentContext,
            String traceId,
            String name,
            SpanKind spanKind,
            Attributes attributes,
            List<LinkData> parentLinks) {
            return SamplingResult.create(
                name.contains("SAMPLE") ? SamplingDecision.RECORD_AND_SAMPLE : SamplingDecision.DROP);
        }

        @Override
        public String getDescription() {
            return "My Sampler Implementation!";
        }

    }

}
