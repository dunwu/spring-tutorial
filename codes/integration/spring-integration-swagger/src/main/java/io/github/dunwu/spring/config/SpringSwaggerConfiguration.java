package io.github.dunwu.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Spring + Swagger 配置
 * <p>
 * Swagger 可以自动扫描 Controller 中定义了 Swagger 注解的信息，从而生成 REST API 文档
 * <p>
 * 文档访问路径：http://<ip>:<port>/<context>/swagger-ui.html。
 * <p>
 * 例：http://localhost:8080/spring-tutorial-integration-swagger/swagger-ui.html
 * <p>
 * 配置参考：<a href= "http://springfox.github.io/springfox/docs/current/#getting-started">SpringFox Docs</a>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-06
 */
@Configuration
@EnableWebMvc
@EnableSwagger2
@ComponentScan(basePackages = { "io.github.dunwu" })
public class SpringSwaggerConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public Docket createDocket() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.any())
                                                      .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("Zhang Peng", "https://github.com/dunwu", "forbreak@163.com");
        return new ApiInfoBuilder().title("开放 REST API 接口")
                                   .description("Spring + Swagger 自动生成开放 REST API 接口")
                                   .version("1.0.0")
                                   .termsOfServiceUrl("https://github.com/dunwu")
                                   .contact(contact)
                                   .build();
    }

}
