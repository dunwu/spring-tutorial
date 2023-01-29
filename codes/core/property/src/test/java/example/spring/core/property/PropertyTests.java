package example.spring.core.property;

import example.spring.core.property.config.DunwuProperties;
import example.spring.core.property.config.DunwuRandomProperties;
import example.spring.core.property.config.GenderEnum;
import example.spring.core.property.config.ValidatedProperties;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ActiveProfiles({ "prop" })
@SpringBootTest(classes = { PropertyApplication.class })
public class PropertyTests {

    private final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

    @Autowired
    private DunwuProperties dunwuProperties;

    @Autowired
    private DunwuRandomProperties dunwuRandomProperties;

    @Autowired
    private ValidatedProperties validatedProperties;

    @Test
    @DisplayName("测试SpringBoot绑定自定义属性")
    public void bindDunwuProperties() {
        Map<String, String> infoMap = new HashMap<>();
        infoMap.put("education", "master's degree");
        infoMap.put("career", "programmer");

        Map<String, List<String>> skillMap = new HashMap<>();
        skillMap.put("JavaCore", Arrays.asList("JDK", "JVM"));
        skillMap.put("JavaWeb", Arrays.asList("Spring", "Spring Boot", "MyBatis"));

        Assertions.assertThat(dunwuProperties.getId()).isEqualTo(1);
        Assertions.assertThat(dunwuProperties.getAuthor()).isEqualTo("Zhang Peng");
        Assertions.assertThat(dunwuProperties.getMail()).isEqualTo("forbreak@163.com");
        Assertions.assertThat(dunwuProperties.getSex()).isEqualTo(GenderEnum.MALE);
        Assertions.assertThat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dunwuProperties.getDate()))
                  .isEqualTo("2019-11-20 12:00:00");
        Assertions.assertThat(dunwuProperties.getAddress()).containsAll(Arrays.asList("China", "Jiangsu", "Nanjing"));
        Assertions.assertThat(dunwuProperties.getInterestList()).containsAll(Arrays.asList("Music", "Game", "Reading"));
        Assertions.assertThat(dunwuProperties.getInfoMap()).containsAllEntriesOf(infoMap);
        Assertions.assertThat(dunwuProperties.getSkillMap()).containsAllEntriesOf(skillMap);

        System.out.format("加载 %s 测试成功。加载属性：%s\n", dunwuProperties.getClass().getSimpleName(),
            dunwuProperties);
    }

    @Test
    @DisplayName("测试SpringBoot绑定自定义随机属性")
    public void bindDunwuRandomProperties() {
        Assertions.assertThat(dunwuRandomProperties).isNotNull();
        Assertions.assertThat(dunwuRandomProperties.getSecret()).isNotNull();
        Assertions.assertThat(dunwuRandomProperties.getNumber()).isNotNull();
        Assertions.assertThat(dunwuRandomProperties.getBigNumber()).isNotNull();
        Assertions.assertThat(dunwuRandomProperties.getUuid()).isNotNull();
        Assertions.assertThat(dunwuRandomProperties.getLessThanTenNum()).isLessThan(10);
        Assertions.assertThat(dunwuRandomProperties.getInRangeNum()).isBetween(1024, 65536);

        System.out.format("加载 %s 测试成功。加载属性：%s\n", dunwuRandomProperties.getClass().getSimpleName(),
            dunwuRandomProperties);
    }

    @Test
    @DisplayName("测试SpringBoot校验自定义属性")
    public void bindValidatedProperties() {
        Assertions.assertThat(validatedProperties.getHost()).isEqualTo("127.0.0.1");
        Assertions.assertThat(validatedProperties.getPort()).isEqualTo(Integer.valueOf(8080));
        System.out.format("加载 %s 测试成功。加载属性：%s\n", validatedProperties.getClass().getSimpleName(),
            validatedProperties);
    }

    @Test
    public void bindInvalidProperties() {
        context.register(PropertyApplication.class);
        TestPropertyValues.of("validator.host:xxxxxx", "validator.port:9090").applyTo(context);
        Assertions.assertThatExceptionOfType(UnsatisfiedDependencyException.class)
                  .isThrownBy(context::refresh)
                  .withMessageContaining("Error creating bean with name");
    }

    @AfterEach
    public void closeContext() {
        context.close();
    }

}
