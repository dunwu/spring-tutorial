package example.trace.opentelemetry;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import io.opentelemetry.context.propagation.TextMapPropagator;
import io.opentelemetry.context.propagation.TextMapSetter;
import io.opentelemetry.semconv.trace.attributes.SemanticAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private RestTemplate restTemplate;
    //
    // // Tell OpenTelemetry to inject the context in the HTTP headers
    // TextMapSetter<HttpServletRequest> setter = (carrier, key, value) -> {
    //     assert carrier != null;
    //     carrier.setAttribute(key, value);
    // };

    @GetMapping("/hello")
    public String hello(@RequestParam("name") String name, @RequestHeader MultiValueMap<String, String> headers,
        HttpServletRequest request) {

        // Tracer tracer = LoggingOpenTelemetrySupport.getTracer();
        // OpenTelemetry openTelemetry = LoggingOpenTelemetrySupport.getOpenTelemetry();
        // TextMapPropagator textMapPropagator = openTelemetry.getPropagators().getTextMapPropagator();
        //
        // Span span = tracer.spanBuilder("/client/hello").setSpanKind(SpanKind.CLIENT).startSpan();
        // try (Scope scope = span.makeCurrent()) {
        //     // Use the Semantic Conventions.
        //     // (Note that to set these, Span does not *need* to be the current instance in Context or Scope.)
        //     span.setAttribute(SemanticAttributes.HTTP_METHOD, "GET");
        //     span.setAttribute(SemanticAttributes.HTTP_URL, request.getRequestURI());
        //
        //     headers.add("traceId", span.getSpanContext().getTraceId());
        //     headers.add("spanId", span.getSpanContext().getSpanId());
        //     System.out.println("traceId: " + span.getSpanContext().getTraceId());
        //     System.out.println("spanId: " + span.getSpanContext().getSpanId());
        //
        //     textMapPropagator.inject(Context.current(), request, setter);
        // } finally {
        //     span.end();
        // }
        //
        Map<String, String> params = new HashMap<>(1);
        params.put("name", name);
        return this.restTemplate.getForObject("http://localhost:8080" + "/server/hello?name={name}",
            String.class, params);
    }

}
