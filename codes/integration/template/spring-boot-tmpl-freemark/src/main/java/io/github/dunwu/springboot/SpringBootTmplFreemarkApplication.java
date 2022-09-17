package io.github.dunwu.springboot;

import freemarker.template.TemplateExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

@Configuration
@SpringBootApplication
public class SpringBootTmplFreemarkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootTmplFreemarkApplication.class, args);
    }

    @Bean
    public freemarker.template.Configuration freemarkConfig() {
        /* ------------------------------------------------------------------------ */
        /* You should do this ONLY ONCE in the whole application life-cycle: */

        /* Create and adjust the configuration singleton */
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(
            freemarker.template.Configuration.VERSION_2_3_22);

        File folder;
        try {
            folder = ResourceUtils.getFile("classpath:templates");
            cfg.setDirectoryForTemplateLoading(folder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        return cfg;
    }

}
