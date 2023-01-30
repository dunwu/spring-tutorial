package example.spring.core.property;

import example.spring.core.property.config.CustomConfig;
import example.spring.core.property.config.DunwuProperties;
import example.spring.core.property.config.ValidatedProperties;
import example.spring.core.property.config.ValidatedPropertiesValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.Validator;

import java.text.SimpleDateFormat;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class PropertyApplication implements CommandLineRunner {

    private final DunwuProperties dunwuProperties;

    private final ValidatedProperties validatedProperties;

    private final CustomConfig.Topics topics;

    /**
     * 注册 Validator Bean 的方法必须为 static 方法，且必须名为 configurationPropertiesValidator
     *
     * @return Validator
     */
    @Bean
    public static Validator configurationPropertiesValidator() {
        return new ValidatedPropertiesValidator();
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(PropertyApplication.class).run(args);
    }

    @Override
    public void run(String... args) {
        log.info("validator.host: {}", validatedProperties.getHost());
        log.info("validator.port: {}", validatedProperties.getPort());
        log.info("profile: {}", dunwuProperties.getProfile());
        log.info("ID: {}", dunwuProperties.getId());
        log.info("作者姓名: {}", dunwuProperties.getAuthor());
        log.info("性别: {}", dunwuProperties.getSex().getValue());
        log.info("日期: {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dunwuProperties.getDate()));
        log.info("邮件: {}", dunwuProperties.getMail());
        log.info("手机号: {}", dunwuProperties.getMobile());
        log.info("Host: {}", dunwuProperties.getHost());
        log.info("=========== 兴趣 ===========");
        dunwuProperties.getInterestList().forEach(log::info);
        log.info("=========== 信息 ===========");
        dunwuProperties.getInfoMap().forEach((key, value) -> log.info("{} : {}", key, value));
        log.info("=========== 技能 ===========");
        dunwuProperties.getSkillMap().forEach((key, value) -> {
            log.info("{} 技术项：", key);
            value.forEach(log::info);
        });

        log.info("custom.topic1: {}", topics.getTopic1());
        log.info("custom.topic2: {}", topics.getTopic2());
    }

}
