package example.spring.trace.sleuth;

import cn.hutool.core.util.ReflectUtil;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.tag.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SampleController {

    @Autowired
    private Tracer tracer;

    @GetMapping("/info")
    public String info(HttpServletRequest request) {
        Span parent = tracer.buildSpan("parent").withTag("mode", "opentracing").start();
        try (Scope scope = tracer.scopeManager().activate(parent)) {

            Tags.SPAN_KIND.set(tracer.activeSpan(), Tags.SPAN_KIND_SERVER);
            Tags.HTTP_METHOD.set(tracer.activeSpan(), "GET");
            Tags.HTTP_URL.set(tracer.activeSpan(), "/info");

            Span child = tracer.buildSpan("child").asChildOf(parent).start();
            try (Scope childScope = tracer.scopeManager().activate(child)) {
                SpanContext context = tracer.activeSpan().context();
                System.out.println("traceId = " + context.toTraceId());
                System.out.println("spanId = " + context.toSpanId());

                // 未提供直接获取 parentId 的方法，但是可以通过下面反射的方式取到
                Object realContext = ReflectUtil.getFieldValue(context, "context");
                String parentId = (String) ReflectUtil.getFieldValue(realContext, "parentIdString");
                System.out.println("parentId = " + parentId);
            } finally {
                child.finish();
            }
        } finally {
            parent.finish();
        }
        return "call info success.";
    }

}
