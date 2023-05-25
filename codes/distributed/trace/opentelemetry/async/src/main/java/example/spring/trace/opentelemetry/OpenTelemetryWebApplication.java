package example.spring.trace.opentelemetry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class OpenTelemetryWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenTelemetryWebApplication.class, args);
    }

}
