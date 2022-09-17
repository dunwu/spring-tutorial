package io.github.dunwu.springboot;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * {@link VelocitySampleBootstrap}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @version 1.0.0
 * @see VelocitySampleBootstrap
 * @since 1.0.0 2016-07-18
 */
@SpringBootApplication
public class VelocitySampleBootstrap {

    public static void main(String[] args) {
        new SpringApplicationBuilder(VelocitySampleBootstrap.class)
            .web(WebApplicationType.SERVLET)
            .run(args);
    }

}
