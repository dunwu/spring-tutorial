package io.github.dunwu.spring.config;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import com.google.common.base.Predicate;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Sets;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2 配置类
 * @author Zhang Peng
 * @see http://springfox.github.io/springfox/docs/current/#configuring-springfox
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /*@Bean
    public Docket configSpringfoxDocketForAll() {
        return new Docket(DocumentationType.SWAGGER_2)
            .produces(Sets.newHashSet("application/json", "application/xml"))
            .consumes(Sets.newHashSet("application/json", "application/xml"))
            .protocols(Sets.newHashSet("http"*//* , "https" *//*))
            .forCodeGeneration(true)
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
            .apiInfo(apiInfo());
    }*/

    @Bean
    public Docket configSpringfoxDocketForAll() {
        return new Docket(DocumentationType.SWAGGER_2)
            .produces(Sets.newHashSet("application/json", "application/xml"))
            .consumes(Sets.newHashSet("application/json", "application/xml"))
            .protocols(Sets.newHashSet("http"/* , "https" */))
            .forCodeGeneration(true)
            .select()
            .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo("API 平台", "使用Swagger UI构建SpringMVC REST风格的可视化文档", "1.0.0",
                "http://localhost:8089/spring-notes-integration-api/v2/api-docs", "forbreak@163.com",
                "Apache License 2.0", "http://www.apache.org/licenses/LICENSE-2.0.html");
        return apiInfo;
    }
}
