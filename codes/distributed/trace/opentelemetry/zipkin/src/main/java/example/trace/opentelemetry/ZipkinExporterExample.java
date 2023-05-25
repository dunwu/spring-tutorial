package example.trace.opentelemetry;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.TracerProvider;
import io.opentelemetry.context.Scope;

public final class ZipkinExporterExample {

    // The Tracer we'll use for the example
    private final Tracer tracer;

    public ZipkinExporterExample(TracerProvider tracerProvider) {
        tracer = tracerProvider.get("example.trace.opentelemetry.ZipkinExporterExample");
    }

    // This method instruments doWork() method
    public void myWonderfulUseCase() {
        // Generate span
        Span span = tracer.spanBuilder("Start my wonderful use case").startSpan();
        try (Scope scope = span.makeCurrent()) {
            // Add some Event to the span
            span.addEvent("Event 0");
            // execute my use case - here we simulate a wait
            doWork();
            // Add some Event to the span
            span.addEvent("Event 1");
        } finally {
            span.end();
        }
    }

    public void doWork() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // ignore in an example
        }
    }

    public static void main(String[] args) {

        // it is important to initialize the OpenTelemetry SDK as early as possible in your process.
        OpenTelemetry openTelemetry = ZipkinOpenTelemetrySupport.getOpenTelemetry();

        TracerProvider tracerProvider = openTelemetry.getTracerProvider();

        // start example
        ZipkinExporterExample example = new ZipkinExporterExample(tracerProvider);
        example.myWonderfulUseCase();

        System.out.println("Bye");
    }

}
