package example.spring.web;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see <a href= "https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/web.html#mvc-config">mvc-config</a>
 * @since 2019-04-21
 */
@Configuration
@EnableWebMvc
@ServletComponentScan(basePackages = "io.github.dunwu")
public class DunwuWebConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowCredentials(true)
            .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH").maxAge(3600);
    }

}
