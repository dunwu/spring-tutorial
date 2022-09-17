package io.github.dunwu.springboot;

import io.github.dunwu.springboot.config.DunwuProperties;
import io.github.dunwu.springboot.config.DunwuRandomProperties;
import io.github.dunwu.springboot.config.GenderEnum;
import io.github.dunwu.springboot.config.ValidatedProperties;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ActiveProfiles({ "test" })
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootPropertyApplicationTest {

    private final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

    @Autowired
    private DunwuProperties dunwuProperties;

    @Autowired
    private DunwuRandomProperties dunwuRandomProperties;

    @Autowired
    private ValidatedProperties validatedProperties;

    @Test
    public void bindDunwuProperties() {
        Map<String, String> infoMap = new HashMap<>();
        infoMap.put("education", "master's degree");
        infoMap.put("career", "programmer");

        Map<String, List<String>> skillMap = new HashMap<>();
        skillMap.put("JavaCore", Arrays.asList("JDK", "JVM"));
        skillMap.put("JavaWeb", Arrays.asList("Spring", "Spring Boot", "MyBatis"));

        assertThat(dunwuProperties.getId()).isEqualTo(1);
        assertThat(dunwuProperties.getAuthor()).isEqualTo("Zhang Peng");
        assertThat(dunwuProperties.getMail()).isEqualTo("forbreak@163.com");
        assertThat(dunwuProperties.getSex()).isEqualTo(GenderEnum.MALE);
        assertThat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dunwuProperties.getDate()))
            .isEqualTo("2019-11-20 12:00:00");
        assertThat(dunwuProperties.getAddress()).containsAll(Arrays.asList("China", "Jiangsu", "Nanjing"));
        assertThat(dunwuProperties.getInterestList()).containsAll(Arrays.asList("Music", "Game", "Reading"));
        assertThat(dunwuProperties.getInfoMap()).containsAllEntriesOf(infoMap);
        assertThat(dunwuProperties.getSkillMap()).containsAllEntriesOf(skillMap);

        System.out.format("加载 %s 测试成功。加载属性：%s\n", dunwuProperties.getClass().getSimpleName(),
            dunwuProperties.toString());
    }

    @Test
    public void bindDunwuRandomProperties() {
        assertThat(dunwuRandomProperties).isNotNull();
        assertThat(dunwuRandomProperties.getSecret()).isNotNull();
        assertThat(dunwuRandomProperties.getNumber()).isNotNull();
        assertThat(dunwuRandomProperties.getBigNumber()).isNotNull();
        assertThat(dunwuRandomProperties.getUuid()).isNotNull();
        assertThat(dunwuRandomProperties.getLessThanTenNum()).isLessThan(10);
        assertThat(dunwuRandomProperties.getInRangeNum()).isBetween(1024, 65536);

        System.out.format("加载 %s 测试成功。加载属性：%s\n", dunwuRandomProperties.getClass().getSimpleName(),
            dunwuRandomProperties.toString());
    }

    @Test
    public void bindValidatedProperties() {
        assertThat(validatedProperties.getHost()).isEqualTo("127.0.0.1");
        assertThat(validatedProperties.getPort()).isEqualTo(Integer.valueOf(8080));
        System.out.format("加载 %s 测试成功。加载属性：%s\n", validatedProperties.getClass().getSimpleName(),
            validatedProperties.toString());
    }

    @Test
    public void bindInvalidProperties() {
        this.context.register(SpringBootPropertyApplication.class);
        this.context.getEnvironment().getSystemProperties();
        TestPropertyValues.of("validator.host:xxxxxx", "validator.port:9090").applyTo(this.context);
        assertThatExceptionOfType(BeanCreationException.class).isThrownBy(this.context::refresh)
            .withMessageContaining("Failed to bind properties under 'validator'");
    }

    @After
    public void closeContext() {
        this.context.close();
    }

}
