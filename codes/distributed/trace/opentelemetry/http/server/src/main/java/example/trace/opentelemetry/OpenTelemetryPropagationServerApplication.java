package example.trace.opentelemetry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OpenTelemetryPropagationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenTelemetryPropagationServerApplication.class, args);
    }

}
